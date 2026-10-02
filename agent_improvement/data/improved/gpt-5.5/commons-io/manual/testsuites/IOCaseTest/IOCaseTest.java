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
 */
class IOCaseTest {

    private static final String EMPTY = "";
    private static final String MIXED_ABC = "Abc";
    private static final String UPPER_ABC = "ABC";
    private static final String UPPER_BC = "BC";
    private static final boolean WINDOWS = File.separatorChar == '\\';

    private void assertAllZero(final byte[] array) {
        for (final byte value : array) {
            assertEquals(0, value);
        }
    }

    private void assertAllZero(final char[] array) {
        for (final char value : array) {
            assertEquals(0, value);
        }
    }

    private IOCase serialize(final IOCase value) throws Exception {
        final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        try (ObjectOutputStream output = new ObjectOutputStream(buffer)) {
            output.writeObject(value);
            output.flush();
        }

        final ByteArrayInputStream inputBuffer = new ByteArrayInputStream(buffer.toByteArray());
        final ObjectInputStream input = new ObjectInputStream(inputBuffer);
        return (IOCase) input.readObject();
    }

    @Test
    void test_checkCompare_case() {
        assertEquals(0, IOCase.SENSITIVE.checkCompareTo(UPPER_ABC, UPPER_ABC));
        assertTrue(IOCase.SENSITIVE.checkCompareTo(UPPER_ABC, "abc") < 0);
        assertTrue(IOCase.SENSITIVE.checkCompareTo("abc", UPPER_ABC) > 0);

        assertEquals(0, IOCase.INSENSITIVE.checkCompareTo(UPPER_ABC, UPPER_ABC));
        assertEquals(0, IOCase.INSENSITIVE.checkCompareTo(UPPER_ABC, "abc"));
        assertEquals(0, IOCase.INSENSITIVE.checkCompareTo("abc", UPPER_ABC));

        assertEquals(0, IOCase.SYSTEM.checkCompareTo(UPPER_ABC, UPPER_ABC));
        assertEquals(WINDOWS, IOCase.SYSTEM.checkCompareTo(UPPER_ABC, "abc") == 0);
        assertEquals(WINDOWS, IOCase.SYSTEM.checkCompareTo("abc", UPPER_ABC) == 0);
    }

    @Test
    void test_checkCompare_functionality() {
        assertTrue(IOCase.SENSITIVE.checkCompareTo(UPPER_ABC, EMPTY) > 0);
        assertTrue(IOCase.SENSITIVE.checkCompareTo(EMPTY, UPPER_ABC) < 0);
        assertTrue(IOCase.SENSITIVE.checkCompareTo(UPPER_ABC, "DEF") < 0);
        assertTrue(IOCase.SENSITIVE.checkCompareTo("DEF", UPPER_ABC) > 0);
        assertEquals(0, IOCase.SENSITIVE.checkCompareTo(UPPER_ABC, UPPER_ABC));
        assertEquals(0, IOCase.SENSITIVE.checkCompareTo(EMPTY, EMPTY));

        assertThrows(NullPointerException.class, () -> IOCase.SENSITIVE.checkCompareTo(UPPER_ABC, null));
        assertThrows(NullPointerException.class, () -> IOCase.SENSITIVE.checkCompareTo(null, UPPER_ABC));
        assertThrows(NullPointerException.class, () -> IOCase.SENSITIVE.checkCompareTo(null, null));
    }

    @Test
    void test_checkEndsWith_case() {
        assertTrue(IOCase.SENSITIVE.checkEndsWith(UPPER_ABC, UPPER_BC));
        assertFalse(IOCase.SENSITIVE.checkEndsWith(UPPER_ABC, "Bc"));

        assertTrue(IOCase.INSENSITIVE.checkEndsWith(UPPER_ABC, UPPER_BC));
        assertTrue(IOCase.INSENSITIVE.checkEndsWith(UPPER_ABC, "Bc"));

        assertTrue(IOCase.SYSTEM.checkEndsWith(UPPER_ABC, UPPER_BC));
        assertEquals(WINDOWS, IOCase.SYSTEM.checkEndsWith(UPPER_ABC, "Bc"));
    }

    @Test
    void test_checkEndsWith_functionality() {
        assertTrue(IOCase.SENSITIVE.checkEndsWith(UPPER_ABC, EMPTY));
        assertFalse(IOCase.SENSITIVE.checkEndsWith(UPPER_ABC, "A"));
        assertFalse(IOCase.SENSITIVE.checkEndsWith(UPPER_ABC, "AB"));
        assertTrue(IOCase.SENSITIVE.checkEndsWith(UPPER_ABC, UPPER_ABC));
        assertTrue(IOCase.SENSITIVE.checkEndsWith(UPPER_ABC, UPPER_BC));
        assertTrue(IOCase.SENSITIVE.checkEndsWith(UPPER_ABC, "C"));
        assertFalse(IOCase.SENSITIVE.checkEndsWith(UPPER_ABC, "ABCD"));
        assertFalse(IOCase.SENSITIVE.checkEndsWith(EMPTY, UPPER_ABC));
        assertTrue(IOCase.SENSITIVE.checkEndsWith(EMPTY, EMPTY));

        assertFalse(IOCase.SENSITIVE.checkEndsWith(UPPER_ABC, null));
        assertFalse(IOCase.SENSITIVE.checkEndsWith(null, UPPER_ABC));
        assertFalse(IOCase.SENSITIVE.checkEndsWith(null, null));
    }

    @Test
    void test_checkEquals_case() {
        assertTrue(IOCase.SENSITIVE.checkEquals(UPPER_ABC, UPPER_ABC));
        assertFalse(IOCase.SENSITIVE.checkEquals(UPPER_ABC, MIXED_ABC));

        assertTrue(IOCase.INSENSITIVE.checkEquals(UPPER_ABC, UPPER_ABC));
        assertTrue(IOCase.INSENSITIVE.checkEquals(UPPER_ABC, MIXED_ABC));

        assertTrue(IOCase.SYSTEM.checkEquals(UPPER_ABC, UPPER_ABC));
        assertEquals(WINDOWS, IOCase.SYSTEM.checkEquals(UPPER_ABC, MIXED_ABC));
    }

    @Test
    void test_checkEquals_functionality() {
        assertFalse(IOCase.SENSITIVE.checkEquals(UPPER_ABC, EMPTY));
        assertFalse(IOCase.SENSITIVE.checkEquals(UPPER_ABC, "A"));
        assertFalse(IOCase.SENSITIVE.checkEquals(UPPER_ABC, "AB"));
        assertTrue(IOCase.SENSITIVE.checkEquals(UPPER_ABC, UPPER_ABC));
        assertFalse(IOCase.SENSITIVE.checkEquals(UPPER_ABC, UPPER_BC));
        assertFalse(IOCase.SENSITIVE.checkEquals(UPPER_ABC, "C"));
        assertFalse(IOCase.SENSITIVE.checkEquals(UPPER_ABC, "ABCD"));
        assertFalse(IOCase.SENSITIVE.checkEquals(EMPTY, UPPER_ABC));
        assertTrue(IOCase.SENSITIVE.checkEquals(EMPTY, EMPTY));

        assertFalse(IOCase.SENSITIVE.checkEquals(UPPER_ABC, null));
        assertFalse(IOCase.SENSITIVE.checkEquals(null, UPPER_ABC));
        assertTrue(IOCase.SENSITIVE.checkEquals(null, null));
    }

    @Test
    void test_checkIndexOf_case() {
        assertEquals(1, IOCase.SENSITIVE.checkIndexOf(UPPER_ABC, 0, UPPER_BC));
        assertEquals(-1, IOCase.SENSITIVE.checkIndexOf(UPPER_ABC, 0, "Bc"));
        assertEquals(-1, IOCase.SENSITIVE.checkIndexOf(null, 0, "Bc"));
        assertEquals(-1, IOCase.SENSITIVE.checkIndexOf(null, 0, null));
        assertEquals(-1, IOCase.SENSITIVE.checkIndexOf(UPPER_ABC, 0, null));

        assertEquals(1, IOCase.INSENSITIVE.checkIndexOf(UPPER_ABC, 0, UPPER_BC));
        assertEquals(1, IOCase.INSENSITIVE.checkIndexOf(UPPER_ABC, 0, "Bc"));

        assertEquals(1, IOCase.SYSTEM.checkIndexOf(UPPER_ABC, 0, UPPER_BC));
        assertEquals(WINDOWS ? 1 : -1, IOCase.SYSTEM.checkIndexOf(UPPER_ABC, 0, "Bc"));
    }

    @Test
    void test_checkIndexOf_functionality() {
        final String alphabetPrefix = "ABCDEFGHIJ";

        assertEquals(0, IOCase.SENSITIVE.checkIndexOf(alphabetPrefix, 0, "A"));
        assertEquals(-1, IOCase.SENSITIVE.checkIndexOf(alphabetPrefix, 1, "A"));
        assertEquals(0, IOCase.SENSITIVE.checkIndexOf(alphabetPrefix, 0, "AB"));
        assertEquals(-1, IOCase.SENSITIVE.checkIndexOf(alphabetPrefix, 1, "AB"));
        assertEquals(0, IOCase.SENSITIVE.checkIndexOf(alphabetPrefix, 0, UPPER_ABC));
        assertEquals(-1, IOCase.SENSITIVE.checkIndexOf(alphabetPrefix, 1, UPPER_ABC));

        assertEquals(3, IOCase.SENSITIVE.checkIndexOf(alphabetPrefix, 0, "D"));
        assertEquals(3, IOCase.SENSITIVE.checkIndexOf(alphabetPrefix, 3, "D"));
        assertEquals(-1, IOCase.SENSITIVE.checkIndexOf(alphabetPrefix, 4, "D"));
        assertEquals(3, IOCase.SENSITIVE.checkIndexOf(alphabetPrefix, 0, "DE"));
        assertEquals(3, IOCase.SENSITIVE.checkIndexOf(alphabetPrefix, 3, "DE"));
        assertEquals(-1, IOCase.SENSITIVE.checkIndexOf(alphabetPrefix, 4, "DE"));
        assertEquals(3, IOCase.SENSITIVE.checkIndexOf(alphabetPrefix, 0, "DEF"));
        assertEquals(3, IOCase.SENSITIVE.checkIndexOf(alphabetPrefix, 3, "DEF"));
        assertEquals(-1, IOCase.SENSITIVE.checkIndexOf(alphabetPrefix, 4, "DEF"));

        assertEquals(9, IOCase.SENSITIVE.checkIndexOf(alphabetPrefix, 0, "J"));
        assertEquals(9, IOCase.SENSITIVE.checkIndexOf(alphabetPrefix, 8, "J"));
        assertEquals(9, IOCase.SENSITIVE.checkIndexOf(alphabetPrefix, 9, "J"));
        assertEquals(8, IOCase.SENSITIVE.checkIndexOf(alphabetPrefix, 0, "IJ"));
        assertEquals(8, IOCase.SENSITIVE.checkIndexOf(alphabetPrefix, 8, "IJ"));
        assertEquals(-1, IOCase.SENSITIVE.checkIndexOf(alphabetPrefix, 9, "IJ"));
        assertEquals(7, IOCase.SENSITIVE.checkIndexOf(alphabetPrefix, 6, "HIJ"));
        assertEquals(7, IOCase.SENSITIVE.checkIndexOf(alphabetPrefix, 7, "HIJ"));
        assertEquals(-1, IOCase.SENSITIVE.checkIndexOf(alphabetPrefix, 8, "HIJ"));

        assertEquals(-1, IOCase.SENSITIVE.checkIndexOf(alphabetPrefix, 0, "DED"));
        assertEquals(-1, IOCase.SENSITIVE.checkIndexOf("DEF", 0, alphabetPrefix));

        assertEquals(-1, IOCase.SENSITIVE.checkIndexOf(UPPER_ABC, 0, null));
        assertEquals(-1, IOCase.SENSITIVE.checkIndexOf(null, 0, UPPER_ABC));
        assertEquals(-1, IOCase.SENSITIVE.checkIndexOf(null, 0, null));
    }

    @Test
    void test_checkRegionMatches_case() {
        assertTrue(IOCase.SENSITIVE.checkRegionMatches(UPPER_ABC, 0, "AB"));
        assertFalse(IOCase.SENSITIVE.checkRegionMatches(UPPER_ABC, 0, "Ab"));

        assertTrue(IOCase.INSENSITIVE.checkRegionMatches(UPPER_ABC, 0, "AB"));
        assertTrue(IOCase.INSENSITIVE.checkRegionMatches(UPPER_ABC, 0, "Ab"));

        assertTrue(IOCase.SYSTEM.checkRegionMatches(UPPER_ABC, 0, "AB"));
        assertEquals(WINDOWS, IOCase.SYSTEM.checkRegionMatches(UPPER_ABC, 0, "Ab"));
    }

    @Test
    void test_checkRegionMatches_functionality() {
        assertTrue(IOCase.SENSITIVE.checkRegionMatches(UPPER_ABC, 0, EMPTY));
        assertTrue(IOCase.SENSITIVE.checkRegionMatches(UPPER_ABC, 0, "A"));
        assertTrue(IOCase.SENSITIVE.checkRegionMatches(UPPER_ABC, 0, "AB"));
        assertTrue(IOCase.SENSITIVE.checkRegionMatches(UPPER_ABC, 0, UPPER_ABC));
        assertFalse(IOCase.SENSITIVE.checkRegionMatches(UPPER_ABC, 0, UPPER_BC));
        assertFalse(IOCase.SENSITIVE.checkRegionMatches(UPPER_ABC, 0, "C"));
        assertFalse(IOCase.SENSITIVE.checkRegionMatches(UPPER_ABC, 0, "ABCD"));
        assertFalse(IOCase.SENSITIVE.checkRegionMatches(EMPTY, 0, UPPER_ABC));
        assertTrue(IOCase.SENSITIVE.checkRegionMatches(EMPTY, 0, EMPTY));

        assertTrue(IOCase.SENSITIVE.checkRegionMatches(UPPER_ABC, 1, EMPTY));
        assertFalse(IOCase.SENSITIVE.checkRegionMatches(UPPER_ABC, 1, "A"));
        assertFalse(IOCase.SENSITIVE.checkRegionMatches(UPPER_ABC, 1, "AB"));
        assertFalse(IOCase.SENSITIVE.checkRegionMatches(UPPER_ABC, 1, UPPER_ABC));
        assertTrue(IOCase.SENSITIVE.checkRegionMatches(UPPER_ABC, 1, UPPER_BC));
        assertFalse(IOCase.SENSITIVE.checkRegionMatches(UPPER_ABC, 1, "C"));
        assertFalse(IOCase.SENSITIVE.checkRegionMatches(UPPER_ABC, 1, "ABCD"));
        assertFalse(IOCase.SENSITIVE.checkRegionMatches(EMPTY, 1, UPPER_ABC));
        assertFalse(IOCase.SENSITIVE.checkRegionMatches(EMPTY, 1, EMPTY));

        assertFalse(IOCase.SENSITIVE.checkRegionMatches(UPPER_ABC, 0, null));
        assertFalse(IOCase.SENSITIVE.checkRegionMatches(null, 0, UPPER_ABC));
        assertFalse(IOCase.SENSITIVE.checkRegionMatches(null, 0, null));
        assertFalse(IOCase.SENSITIVE.checkRegionMatches(UPPER_ABC, 1, null));
        assertFalse(IOCase.SENSITIVE.checkRegionMatches(null, 1, UPPER_ABC));
        assertFalse(IOCase.SENSITIVE.checkRegionMatches(null, 1, null));
    }

    @Test
    void test_checkStartsWith_case() {
        assertTrue(IOCase.SENSITIVE.checkStartsWith(UPPER_ABC, "AB"));
        assertFalse(IOCase.SENSITIVE.checkStartsWith(UPPER_ABC, "Ab"));

        assertTrue(IOCase.INSENSITIVE.checkStartsWith(UPPER_ABC, "AB"));
        assertTrue(IOCase.INSENSITIVE.checkStartsWith(UPPER_ABC, "Ab"));

        assertTrue(IOCase.SYSTEM.checkStartsWith(UPPER_ABC, "AB"));
        assertEquals(WINDOWS, IOCase.SYSTEM.checkStartsWith(UPPER_ABC, "Ab"));
    }

    @Test
    void test_checkStartsWith_functionality() {
        assertTrue(IOCase.SENSITIVE.checkStartsWith(UPPER_ABC, EMPTY));
        assertTrue(IOCase.SENSITIVE.checkStartsWith(UPPER_ABC, "A"));
        assertTrue(IOCase.SENSITIVE.checkStartsWith(UPPER_ABC, "AB"));
        assertTrue(IOCase.SENSITIVE.checkStartsWith(UPPER_ABC, UPPER_ABC));
        assertFalse(IOCase.SENSITIVE.checkStartsWith(UPPER_ABC, UPPER_BC));
        assertFalse(IOCase.SENSITIVE.checkStartsWith(UPPER_ABC, "C"));
        assertFalse(IOCase.SENSITIVE.checkStartsWith(UPPER_ABC, "ABCD"));
        assertFalse(IOCase.SENSITIVE.checkStartsWith(EMPTY, UPPER_ABC));
        assertTrue(IOCase.SENSITIVE.checkStartsWith(EMPTY, EMPTY));

        assertFalse(IOCase.SENSITIVE.checkStartsWith(UPPER_ABC, null));
        assertFalse(IOCase.SENSITIVE.checkStartsWith(null, UPPER_ABC));
        assertFalse(IOCase.SENSITIVE.checkStartsWith(null, null));
    }

    @Test
    void test_forName() {
        assertEquals(IOCase.SENSITIVE, IOCase.forName("Sensitive"));
        assertEquals(IOCase.INSENSITIVE, IOCase.forName("Insensitive"));
        assertEquals(IOCase.SYSTEM, IOCase.forName("System"));
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
        try (ScratchBytes firstScratch = IOUtils.ScratchBytes.get()) {
            firstArray = firstScratch.array();
            assertAllZero(firstArray);
            Arrays.fill(firstArray, (byte) 1);

            try (ScratchBytes secondScratch = IOUtils.ScratchBytes.get()) {
                assertNotSame(firstScratch, secondScratch);
                final byte[] secondArray = secondScratch.array();
                assertAllZero(secondArray);
                assertNotSame(firstArray, secondArray);
            }
        }

        try (ScratchBytes reusedScratch = IOUtils.ScratchBytes.get()) {
            final byte[] reusedArray = reusedScratch.array();
            assertAllZero(reusedArray);
            assertSame(firstArray, reusedArray);
        }
    }

    @Test
    void test_getScratchCharArray() {
        final char[] firstArray;
        try (ScratchChars firstScratch = IOUtils.ScratchChars.get()) {
            firstArray = firstScratch.array();
            assertAllZero(firstArray);
            Arrays.fill(firstArray, (char) 1);

            try (ScratchChars secondScratch = IOUtils.ScratchChars.get()) {
                final char[] secondArray = secondScratch.array();
                assertAllZero(secondArray);
                assertNotSame(firstArray, secondArray);
            }
        }

        try (ScratchChars reusedScratch = IOUtils.ScratchChars.get()) {
            final char[] reusedArray = reusedScratch.array();
            assertAllZero(reusedArray);
            assertSame(firstArray, reusedArray);
        }
    }

    @Test
    void test_isCaseSensitive() {
        assertTrue(IOCase.SENSITIVE.isCaseSensitive());
        assertFalse(IOCase.INSENSITIVE.isCaseSensitive());
        assertEquals(!WINDOWS, IOCase.SYSTEM.isCaseSensitive());
    }

    @Test
    void test_isCaseSensitive_static() {
        assertTrue(IOCase.isCaseSensitive(IOCase.SENSITIVE));
        assertFalse(IOCase.isCaseSensitive(IOCase.INSENSITIVE));
        assertEquals(!WINDOWS, IOCase.isCaseSensitive(IOCase.SYSTEM));
    }

    @Test
    void test_serialization() throws Exception {
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
