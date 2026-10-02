/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;

import org.apache.commons.io.IOUtils.ScratchBytes;
import org.apache.commons.io.IOUtils.ScratchChars;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link IOCase}.
 * <p>
 * Most tests come in two flavors:
 * </p>
 * <ul>
 *   <li>{@code *_case} tests verify how each {@link IOCase} constant treats letter
 *       case. {@link IOCase#SENSITIVE} distinguishes case, {@link IOCase#INSENSITIVE}
 *       ignores it, and {@link IOCase#SYSTEM} follows the host OS (case-insensitive on
 *       Windows, case-sensitive elsewhere).</li>
 *   <li>{@code *_functionality} tests verify the underlying matching logic (prefixes,
 *       suffixes, indices, null handling) using only {@link IOCase#SENSITIVE}.</li>
 * </ul>
 */
class IOCaseTest {

    /**
     * Whether this test is running on Windows. On Windows {@link IOCase#SYSTEM} behaves
     * case-insensitively; on other platforms it behaves case-sensitively. The expected
     * results of {@code SYSTEM} assertions are derived from this flag.
     */
    private static final boolean IS_WINDOWS = File.separatorChar == '\\';

    /**
     * Asserts that every element of the array is zero, i.e. the array is freshly cleared.
     */
    private void assertAllZeros(final byte[] array) {
        for (final byte element : array) {
            assertEquals(0, element);
        }
    }

    /**
     * Asserts that every element of the array is zero, i.e. the array is freshly cleared.
     */
    private void assertAllZeros(final char[] array) {
        for (final char element : array) {
            assertEquals(0, element);
        }
    }

    /**
     * Serializes then deserializes the given {@link IOCase}, returning the round-tripped value.
     */
    private IOCase serialize(final IOCase value) throws Exception {
        final ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
        try (ObjectOutputStream objectOut = new ObjectOutputStream(bytesOut)) {
            objectOut.writeObject(value);
            objectOut.flush();
        }
        final ByteArrayInputStream bytesIn = new ByteArrayInputStream(bytesOut.toByteArray());
        final ObjectInputStream objectIn = new ObjectInputStream(bytesIn);
        return (IOCase) objectIn.readObject();
    }

    @Test
    void test_checkCompare_case() {
        // SENSITIVE: differing case yields a non-zero, ordered comparison.
        assertEquals(0, IOCase.SENSITIVE.checkCompareTo("ABC", "ABC"));
        assertTrue(IOCase.SENSITIVE.checkCompareTo("ABC", "abc") < 0);
        assertTrue(IOCase.SENSITIVE.checkCompareTo("abc", "ABC") > 0);

        // INSENSITIVE: differing case compares as equal.
        assertEquals(0, IOCase.INSENSITIVE.checkCompareTo("ABC", "ABC"));
        assertEquals(0, IOCase.INSENSITIVE.checkCompareTo("ABC", "abc"));
        assertEquals(0, IOCase.INSENSITIVE.checkCompareTo("abc", "ABC"));

        // SYSTEM: case is ignored only on Windows.
        assertEquals(0, IOCase.SYSTEM.checkCompareTo("ABC", "ABC"));
        assertEquals(IS_WINDOWS, IOCase.SYSTEM.checkCompareTo("ABC", "abc") == 0);
        assertEquals(IS_WINDOWS, IOCase.SYSTEM.checkCompareTo("abc", "ABC") == 0);
    }

    @Test
    void test_checkCompare_functionality() {
        // Ordering follows String.compareTo semantics.
        assertTrue(IOCase.SENSITIVE.checkCompareTo("ABC", "") > 0);
        assertTrue(IOCase.SENSITIVE.checkCompareTo("", "ABC") < 0);
        assertTrue(IOCase.SENSITIVE.checkCompareTo("ABC", "DEF") < 0);
        assertTrue(IOCase.SENSITIVE.checkCompareTo("DEF", "ABC") > 0);
        assertEquals(0, IOCase.SENSITIVE.checkCompareTo("ABC", "ABC"));
        assertEquals(0, IOCase.SENSITIVE.checkCompareTo("", ""));

        // Comparing against null is not allowed.
        assertThrows(NullPointerException.class, () -> IOCase.SENSITIVE.checkCompareTo("ABC", null));
        assertThrows(NullPointerException.class, () -> IOCase.SENSITIVE.checkCompareTo(null, "ABC"));
        assertThrows(NullPointerException.class, () -> IOCase.SENSITIVE.checkCompareTo(null, null));
    }

    @Test
    void test_checkEndsWith_case() {
        // SENSITIVE: suffix must match exactly, including case.
        assertTrue(IOCase.SENSITIVE.checkEndsWith("ABC", "BC"));
        assertFalse(IOCase.SENSITIVE.checkEndsWith("ABC", "Bc"));

        // INSENSITIVE: suffix matches regardless of case.
        assertTrue(IOCase.INSENSITIVE.checkEndsWith("ABC", "BC"));
        assertTrue(IOCase.INSENSITIVE.checkEndsWith("ABC", "Bc"));

        // SYSTEM: case-differing suffix matches only on Windows.
        assertTrue(IOCase.SYSTEM.checkEndsWith("ABC", "BC"));
        assertEquals(IS_WINDOWS, IOCase.SYSTEM.checkEndsWith("ABC", "Bc"));
    }

    @Test
    void test_checkEndsWith_functionality() {
        // A non-null string ends with any of its own suffixes (and the empty string).
        assertTrue(IOCase.SENSITIVE.checkEndsWith("ABC", ""));
        assertFalse(IOCase.SENSITIVE.checkEndsWith("ABC", "A"));
        assertFalse(IOCase.SENSITIVE.checkEndsWith("ABC", "AB"));
        assertTrue(IOCase.SENSITIVE.checkEndsWith("ABC", "ABC"));
        assertTrue(IOCase.SENSITIVE.checkEndsWith("ABC", "BC"));
        assertTrue(IOCase.SENSITIVE.checkEndsWith("ABC", "C"));
        assertFalse(IOCase.SENSITIVE.checkEndsWith("ABC", "ABCD"));
        assertFalse(IOCase.SENSITIVE.checkEndsWith("", "ABC"));
        assertTrue(IOCase.SENSITIVE.checkEndsWith("", ""));

        // Any null argument yields false.
        assertFalse(IOCase.SENSITIVE.checkEndsWith("ABC", null));
        assertFalse(IOCase.SENSITIVE.checkEndsWith(null, "ABC"));
        assertFalse(IOCase.SENSITIVE.checkEndsWith(null, null));
    }

    @Test
    void test_checkEquals_case() {
        // SENSITIVE: equal only when case also matches.
        assertTrue(IOCase.SENSITIVE.checkEquals("ABC", "ABC"));
        assertFalse(IOCase.SENSITIVE.checkEquals("ABC", "Abc"));

        // INSENSITIVE: equal regardless of case.
        assertTrue(IOCase.INSENSITIVE.checkEquals("ABC", "ABC"));
        assertTrue(IOCase.INSENSITIVE.checkEquals("ABC", "Abc"));

        // SYSTEM: case-differing strings are equal only on Windows.
        assertTrue(IOCase.SYSTEM.checkEquals("ABC", "ABC"));
        assertEquals(IS_WINDOWS, IOCase.SYSTEM.checkEquals("ABC", "Abc"));
    }

    @Test
    void test_checkEquals_functionality() {
        // Equality requires the whole string to match, not just a prefix or suffix.
        assertFalse(IOCase.SENSITIVE.checkEquals("ABC", ""));
        assertFalse(IOCase.SENSITIVE.checkEquals("ABC", "A"));
        assertFalse(IOCase.SENSITIVE.checkEquals("ABC", "AB"));
        assertTrue(IOCase.SENSITIVE.checkEquals("ABC", "ABC"));
        assertFalse(IOCase.SENSITIVE.checkEquals("ABC", "BC"));
        assertFalse(IOCase.SENSITIVE.checkEquals("ABC", "C"));
        assertFalse(IOCase.SENSITIVE.checkEquals("ABC", "ABCD"));
        assertFalse(IOCase.SENSITIVE.checkEquals("", "ABC"));
        assertTrue(IOCase.SENSITIVE.checkEquals("", ""));

        // Null handling: false unless both arguments are null.
        assertFalse(IOCase.SENSITIVE.checkEquals("ABC", null));
        assertFalse(IOCase.SENSITIVE.checkEquals(null, "ABC"));
        assertTrue(IOCase.SENSITIVE.checkEquals(null, null));
    }

    @Test
    void test_checkIndexOf_case() {
        // SENSITIVE: search term must match the host string's case.
        assertEquals(1,  IOCase.SENSITIVE.checkIndexOf("ABC", 0, "BC"));
        assertEquals(-1, IOCase.SENSITIVE.checkIndexOf("ABC", 0, "Bc"));
        assertEquals(-1, IOCase.SENSITIVE.checkIndexOf(null, 0, "Bc"));
        assertEquals(-1, IOCase.SENSITIVE.checkIndexOf(null, 0, null));
        assertEquals(-1, IOCase.SENSITIVE.checkIndexOf("ABC", 0, null));

        // INSENSITIVE: case of the search term is ignored.
        assertEquals(1, IOCase.INSENSITIVE.checkIndexOf("ABC", 0, "BC"));
        assertEquals(1, IOCase.INSENSITIVE.checkIndexOf("ABC", 0, "Bc"));

        // SYSTEM: case-differing search matches only on Windows.
        assertEquals(1, IOCase.SYSTEM.checkIndexOf("ABC", 0, "BC"));
        assertEquals(IS_WINDOWS ? 1 : -1, IOCase.SYSTEM.checkIndexOf("ABC", 0, "Bc"));
    }

    @Test
    void test_checkIndexOf_functionality() {
        final String haystack = "ABCDEFGHIJ";

        // Match at the start of the string; raising the start index past it fails.
        assertEquals(0,   IOCase.SENSITIVE.checkIndexOf(haystack, 0, "A"));
        assertEquals(-1,  IOCase.SENSITIVE.checkIndexOf(haystack, 1, "A"));
        assertEquals(0,   IOCase.SENSITIVE.checkIndexOf(haystack, 0, "AB"));
        assertEquals(-1,  IOCase.SENSITIVE.checkIndexOf(haystack, 1, "AB"));
        assertEquals(0,   IOCase.SENSITIVE.checkIndexOf(haystack, 0, "ABC"));
        assertEquals(-1,  IOCase.SENSITIVE.checkIndexOf(haystack, 1, "ABC"));

        // Match in the middle; found while the start index is at or before the match.
        assertEquals(3,   IOCase.SENSITIVE.checkIndexOf(haystack, 0, "D"));
        assertEquals(3,   IOCase.SENSITIVE.checkIndexOf(haystack, 3, "D"));
        assertEquals(-1,  IOCase.SENSITIVE.checkIndexOf(haystack, 4, "D"));
        assertEquals(3,   IOCase.SENSITIVE.checkIndexOf(haystack, 0, "DE"));
        assertEquals(3,   IOCase.SENSITIVE.checkIndexOf(haystack, 3, "DE"));
        assertEquals(-1,  IOCase.SENSITIVE.checkIndexOf(haystack, 4, "DE"));
        assertEquals(3,   IOCase.SENSITIVE.checkIndexOf(haystack, 0, "DEF"));
        assertEquals(3,   IOCase.SENSITIVE.checkIndexOf(haystack, 3, "DEF"));
        assertEquals(-1,  IOCase.SENSITIVE.checkIndexOf(haystack, 4, "DEF"));

        // Match at the end of the string.
        assertEquals(9,   IOCase.SENSITIVE.checkIndexOf(haystack, 0, "J"));
        assertEquals(9,   IOCase.SENSITIVE.checkIndexOf(haystack, 8, "J"));
        assertEquals(9,   IOCase.SENSITIVE.checkIndexOf(haystack, 9, "J"));
        assertEquals(8,   IOCase.SENSITIVE.checkIndexOf(haystack, 0, "IJ"));
        assertEquals(8,   IOCase.SENSITIVE.checkIndexOf(haystack, 8, "IJ"));
        assertEquals(-1,  IOCase.SENSITIVE.checkIndexOf(haystack, 9, "IJ"));
        assertEquals(7,   IOCase.SENSITIVE.checkIndexOf(haystack, 6, "HIJ"));
        assertEquals(7,   IOCase.SENSITIVE.checkIndexOf(haystack, 7, "HIJ"));
        assertEquals(-1,  IOCase.SENSITIVE.checkIndexOf(haystack, 8, "HIJ"));

        // Search term absent from the string.
        assertEquals(-1,   IOCase.SENSITIVE.checkIndexOf(haystack, 0, "DED"));

        // Search term longer than the string can never match.
        assertEquals(-1,   IOCase.SENSITIVE.checkIndexOf("DEF", 0, "ABCDEFGHIJ"));

        // Any null argument yields -1.
        assertEquals(-1, IOCase.SENSITIVE.checkIndexOf("ABC", 0, null));
        assertEquals(-1, IOCase.SENSITIVE.checkIndexOf(null, 0, "ABC"));
        assertEquals(-1, IOCase.SENSITIVE.checkIndexOf(null, 0, null));
    }

    @Test
    void test_checkRegionMatches_case() {
        // SENSITIVE: the region must match the search term's case.
        assertTrue(IOCase.SENSITIVE.checkRegionMatches("ABC", 0, "AB"));
        assertFalse(IOCase.SENSITIVE.checkRegionMatches("ABC", 0, "Ab"));

        // INSENSITIVE: case is ignored within the region.
        assertTrue(IOCase.INSENSITIVE.checkRegionMatches("ABC", 0, "AB"));
        assertTrue(IOCase.INSENSITIVE.checkRegionMatches("ABC", 0, "Ab"));

        // SYSTEM: case-differing region matches only on Windows.
        assertTrue(IOCase.SYSTEM.checkRegionMatches("ABC", 0, "AB"));
        assertEquals(IS_WINDOWS, IOCase.SYSTEM.checkRegionMatches("ABC", 0, "Ab"));
    }

    @Test
    void test_checkRegionMatches_functionality() {
        // Region anchored at index 0: matches any prefix (including the empty string).
        assertTrue(IOCase.SENSITIVE.checkRegionMatches("ABC", 0, ""));
        assertTrue(IOCase.SENSITIVE.checkRegionMatches("ABC", 0, "A"));
        assertTrue(IOCase.SENSITIVE.checkRegionMatches("ABC", 0, "AB"));
        assertTrue(IOCase.SENSITIVE.checkRegionMatches("ABC", 0, "ABC"));
        assertFalse(IOCase.SENSITIVE.checkRegionMatches("ABC", 0, "BC"));
        assertFalse(IOCase.SENSITIVE.checkRegionMatches("ABC", 0, "C"));
        assertFalse(IOCase.SENSITIVE.checkRegionMatches("ABC", 0, "ABCD"));
        assertFalse(IOCase.SENSITIVE.checkRegionMatches("", 0, "ABC"));
        assertTrue(IOCase.SENSITIVE.checkRegionMatches("", 0, ""));

        // Region anchored at index 1: matches the substring starting from "BC".
        assertTrue(IOCase.SENSITIVE.checkRegionMatches("ABC", 1, ""));
        assertFalse(IOCase.SENSITIVE.checkRegionMatches("ABC", 1, "A"));
        assertFalse(IOCase.SENSITIVE.checkRegionMatches("ABC", 1, "AB"));
        assertFalse(IOCase.SENSITIVE.checkRegionMatches("ABC", 1, "ABC"));
        assertTrue(IOCase.SENSITIVE.checkRegionMatches("ABC", 1, "BC"));
        assertFalse(IOCase.SENSITIVE.checkRegionMatches("ABC", 1, "C"));
        assertFalse(IOCase.SENSITIVE.checkRegionMatches("ABC", 1, "ABCD"));
        assertFalse(IOCase.SENSITIVE.checkRegionMatches("", 1, "ABC"));
        assertFalse(IOCase.SENSITIVE.checkRegionMatches("", 1, ""));

        // Any null argument yields false, at any start index.
        assertFalse(IOCase.SENSITIVE.checkRegionMatches("ABC", 0, null));
        assertFalse(IOCase.SENSITIVE.checkRegionMatches(null, 0, "ABC"));
        assertFalse(IOCase.SENSITIVE.checkRegionMatches(null, 0, null));
        assertFalse(IOCase.SENSITIVE.checkRegionMatches("ABC", 1, null));
        assertFalse(IOCase.SENSITIVE.checkRegionMatches(null, 1, "ABC"));
        assertFalse(IOCase.SENSITIVE.checkRegionMatches(null, 1, null));
    }

    @Test
    void test_checkStartsWith_case() {
        // SENSITIVE: prefix must match exactly, including case.
        assertTrue(IOCase.SENSITIVE.checkStartsWith("ABC", "AB"));
        assertFalse(IOCase.SENSITIVE.checkStartsWith("ABC", "Ab"));

        // INSENSITIVE: prefix matches regardless of case.
        assertTrue(IOCase.INSENSITIVE.checkStartsWith("ABC", "AB"));
        assertTrue(IOCase.INSENSITIVE.checkStartsWith("ABC", "Ab"));

        // SYSTEM: case-differing prefix matches only on Windows.
        assertTrue(IOCase.SYSTEM.checkStartsWith("ABC", "AB"));
        assertEquals(IS_WINDOWS, IOCase.SYSTEM.checkStartsWith("ABC", "Ab"));
    }

    @Test
    void test_checkStartsWith_functionality() {
        // A non-null string starts with any of its own prefixes (and the empty string).
        assertTrue(IOCase.SENSITIVE.checkStartsWith("ABC", ""));
        assertTrue(IOCase.SENSITIVE.checkStartsWith("ABC", "A"));
        assertTrue(IOCase.SENSITIVE.checkStartsWith("ABC", "AB"));
        assertTrue(IOCase.SENSITIVE.checkStartsWith("ABC", "ABC"));
        assertFalse(IOCase.SENSITIVE.checkStartsWith("ABC", "BC"));
        assertFalse(IOCase.SENSITIVE.checkStartsWith("ABC", "C"));
        assertFalse(IOCase.SENSITIVE.checkStartsWith("ABC", "ABCD"));
        assertFalse(IOCase.SENSITIVE.checkStartsWith("", "ABC"));
        assertTrue(IOCase.SENSITIVE.checkStartsWith("", ""));

        // Any null argument yields false.
        assertFalse(IOCase.SENSITIVE.checkStartsWith("ABC", null));
        assertFalse(IOCase.SENSITIVE.checkStartsWith(null, "ABC"));
        assertFalse(IOCase.SENSITIVE.checkStartsWith(null, null));
    }

    @Test
    void test_forName() {
        // Each constant is recoverable from its display name.
        assertEquals(IOCase.SENSITIVE, IOCase.forName("Sensitive"));
        assertEquals(IOCase.INSENSITIVE, IOCase.forName("Insensitive"));
        assertEquals(IOCase.SYSTEM, IOCase.forName("System"));

        // Unknown or null names are rejected.
        assertThrows(IllegalArgumentException.class, () -> IOCase.forName("Blah"));
        assertThrows(IllegalArgumentException.class, () -> IOCase.forName(null));
    }

    @Test
    void test_getName() {
        assertEquals("Sensitive", IOCase.SENSITIVE.getName());
        assertEquals("Insensitive", IOCase.INSENSITIVE.getName());
        assertEquals("System", IOCase.SYSTEM.getName());
    }

    @Test
    void test_getScratchByteArray() {
        final byte[] firstArray;
        try (ScratchBytes scratch = IOUtils.ScratchBytes.get()) {
            firstArray = scratch.array();
            assertAllZeros(firstArray);
            // Dirty the array so we can later confirm it gets cleared before reuse.
            Arrays.fill(firstArray, (byte) 1);

            // While the first scratch is still open, a second get() must hand out a
            // distinct, independently-cleared array.
            try (ScratchBytes scratch2 = IOUtils.ScratchBytes.get()) {
                assertNotSame(scratch, scratch2);
                final byte[] secondArray = scratch2.array();
                assertAllZeros(secondArray);
                assertNotSame(firstArray, secondArray);
            }
        }
        // After the first scratch is closed, its array is reset and handed back out.
        try (ScratchBytes scratch = IOUtils.ScratchBytes.get()) {
            final byte[] reusedArray = scratch.array();
            assertAllZeros(reusedArray);
            assertSame(firstArray, reusedArray);
        }
    }

    @Test
    void test_getScratchCharArray() {
        final char[] firstArray;
        try (ScratchChars scratch = IOUtils.ScratchChars.get()) {
            firstArray = scratch.array();
            assertAllZeros(firstArray);
            // Dirty the array so we can later confirm it gets cleared before reuse.
            Arrays.fill(firstArray, (char) 1);

            // While the first scratch is still open, a second get() must hand out a
            // distinct, independently-cleared array.
            try (ScratchChars scratch2 = IOUtils.ScratchChars.get()) {
                final char[] secondArray = scratch2.array();
                assertAllZeros(secondArray);
                assertNotSame(firstArray, secondArray);
            }
        }
        // After the first scratch is closed, its array is reset and handed back out.
        try (ScratchChars scratch = IOUtils.ScratchChars.get()) {
            final char[] reusedArray = scratch.array();
            assertAllZeros(reusedArray);
            assertSame(firstArray, reusedArray);
        }
    }

    @Test
    void test_isCaseSensitive() {
        assertTrue(IOCase.SENSITIVE.isCaseSensitive());
        assertFalse(IOCase.INSENSITIVE.isCaseSensitive());
        // SYSTEM is case-sensitive everywhere except Windows.
        assertEquals(!IS_WINDOWS, IOCase.SYSTEM.isCaseSensitive());
    }

    @Test
    void test_isCaseSensitive_static() {
        assertTrue(IOCase.isCaseSensitive(IOCase.SENSITIVE));
        assertFalse(IOCase.isCaseSensitive(IOCase.INSENSITIVE));
        // SYSTEM is case-sensitive everywhere except Windows.
        assertEquals(!IS_WINDOWS, IOCase.isCaseSensitive(IOCase.SYSTEM));
    }

    @Test
    void test_serialization() throws Exception {
        // Deserialization must resolve back to the singleton enum constant.
        assertSame(IOCase.SENSITIVE, serialize(IOCase.SENSITIVE));
        assertSame(IOCase.INSENSITIVE, serialize(IOCase.INSENSITIVE));
        assertSame(IOCase.SYSTEM, serialize(IOCase.SYSTEM));
    }

    @Test
    void test_toString() {
        assertEquals("Sensitive", IOCase.SENSITIVE.toString());
        assertEquals("Insensitive", IOCase.INSENSITIVE.toString());
        assertEquals("System", IOCase.SYSTEM.toString());
    }

}
