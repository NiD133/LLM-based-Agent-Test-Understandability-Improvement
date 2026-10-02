/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.commons.lang3;

import static org.apache.commons.lang3.LangAssertions.assertIllegalArgumentException;
import static org.apache.commons.lang3.LangAssertions.assertNullPointerException;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.ToIntFunction;

import org.junit.jupiter.api.Test;

/**
 * An enum with exactly 64 constants ({@code A00}..{@code A63}).
 *
 * <p>This is the largest enum that still fits in a single {@code long} bit vector,
 * so it is used to confirm that {@link EnumUtils} handles the highest bit positions
 * (such as bit 63, which is {@link Long#MIN_VALUE}) without any {@code int}/{@code long}
 * conversion issues.</p>
 */
enum Enum64 {
    A00, A01, A02, A03, A04, A05, A06, A07, A08, A09, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21, A22,
    A23, A24, A25, A26, A27, A28, A29, A30, A31, A32, A33, A34, A35, A36, A37, A38, A39, A40, A41, A42, A43, A44, A45,
    A46, A47, A48, A49, A50, A51, A52, A53, A54, A55, A56, A57, A58, A59, A60, A61, A62, A63
}

/**
 * Tests {@link EnumUtils}.
 *
 * <p>Several tests rely on the {@link Traffic} enum, whose three constants map to the
 * following single-{@code long} bit vector positions:</p>
 * <ul>
 *   <li>{@code RED}   &rarr; ordinal 0 &rarr; bit value {@code 1L} ({@code 0b001})</li>
 *   <li>{@code AMBER} &rarr; ordinal 1 &rarr; bit value {@code 2L} ({@code 0b010})</li>
 *   <li>{@code GREEN} &rarr; ordinal 2 &rarr; bit value {@code 4L} ({@code 0b100})</li>
 * </ul>
 *
 * <p>A combination of constants is therefore the bitwise OR of the individual values,
 * e.g. {@code RED + AMBER} is {@code 3L} and all three together is {@code 7L}.</p>
 */
class EnumUtilsTest extends AbstractLangTest {

    /**
     * Asserts that a {@code long[]} produced by {@code EnumUtils} matches the expected longs.
     *
     * @param actual   the array returned by the method under test.
     * @param expected the expected long values, in order.
     */
    private void assertLongArrayEquals(final long[] actual, final long... expected) {
        assertArrayEquals(expected, actual);
    }

    @Test
    void testConstructable() {
        // enforce public constructor
        new EnumUtils();
    }

    @Test
    void testGenerateBitVector() {
        // Single and combined Traffic constants map to OR-ed bit values (RED=1, AMBER=2, GREEN=4).
        assertEquals(0L, EnumUtils.generateBitVector(Traffic.class, EnumSet.noneOf(Traffic.class)));
        assertEquals(1L, EnumUtils.generateBitVector(Traffic.class, EnumSet.of(Traffic.RED)));
        assertEquals(2L, EnumUtils.generateBitVector(Traffic.class, EnumSet.of(Traffic.AMBER)));
        assertEquals(4L, EnumUtils.generateBitVector(Traffic.class, EnumSet.of(Traffic.GREEN)));
        assertEquals(3L, EnumUtils.generateBitVector(Traffic.class, EnumSet.of(Traffic.RED, Traffic.AMBER)));
        assertEquals(5L, EnumUtils.generateBitVector(Traffic.class, EnumSet.of(Traffic.RED, Traffic.GREEN)));
        assertEquals(6L, EnumUtils.generateBitVector(Traffic.class, EnumSet.of(Traffic.AMBER, Traffic.GREEN)));
        assertEquals(7L,
            EnumUtils.generateBitVector(Traffic.class, EnumSet.of(Traffic.RED, Traffic.AMBER, Traffic.GREEN)));

        // 64-value enum: verify high bit positions survive with no int<->long conversion issue.
        assertEquals(1L << 31, EnumUtils.generateBitVector(Enum64.class, EnumSet.of(Enum64.A31)));
        assertEquals(1L << 32, EnumUtils.generateBitVector(Enum64.class, EnumSet.of(Enum64.A32)));
        assertEquals(1L << 63, EnumUtils.generateBitVector(Enum64.class, EnumSet.of(Enum64.A63)));
        // Bit 63 set is exactly Long.MIN_VALUE.
        assertEquals(Long.MIN_VALUE, EnumUtils.generateBitVector(Enum64.class, EnumSet.of(Enum64.A63)));
    }

    @Test
    void testGenerateBitVector_longClass() {
        // An enum with more than 64 constants cannot fit in a single long.
        assertIllegalArgumentException(
            () -> EnumUtils.generateBitVector(TooMany.class, EnumSet.of(TooMany.A1)));
    }

    @Test
    void testGenerateBitVector_longClassWithArray() {
        // Same constraint applies to the varargs overload.
        assertIllegalArgumentException(() -> EnumUtils.generateBitVector(TooMany.class, TooMany.A1));
    }

    @SuppressWarnings("unchecked")
    @Test
    void testGenerateBitVector_nonEnumClass() {
        @SuppressWarnings("rawtypes")
        final Class rawType = Object.class;
        @SuppressWarnings("rawtypes")
        final List rawList = new ArrayList();
        assertIllegalArgumentException(() -> EnumUtils.generateBitVector(rawType, rawList));
    }

    @SuppressWarnings("unchecked")
    @Test
    void testGenerateBitVector_nonEnumClassWithArray() {
        @SuppressWarnings("rawtypes")
        final Class rawType = Object.class;
        assertIllegalArgumentException(() -> EnumUtils.generateBitVector(rawType));
    }

    @Test
    void testGenerateBitVector_nullArray() {
        assertNullPointerException(() -> EnumUtils.generateBitVector(Traffic.class, (Traffic[]) null));
    }

    @Test
    void testGenerateBitVector_nullArrayElement() {
        assertIllegalArgumentException(
            () -> EnumUtils.generateBitVector(Traffic.class, Traffic.RED, null));
    }

    @Test
    void testGenerateBitVector_nullClass() {
        assertNullPointerException(() -> EnumUtils.generateBitVector(null, EnumSet.of(Traffic.RED)));
    }

    @Test
    void testGenerateBitVector_nullClassWithArray() {
        assertNullPointerException(() -> EnumUtils.generateBitVector(null, Traffic.RED));
    }

    @Test
    void testGenerateBitVector_nullElement() {
        assertNullPointerException(
            () -> EnumUtils.generateBitVector(Traffic.class, Arrays.asList(Traffic.RED, null)));
    }

    @Test
    void testGenerateBitVector_nullIterable() {
        assertNullPointerException(
            () -> EnumUtils.generateBitVector(Traffic.class, (Iterable<Traffic>) null));
    }

    @Test
    void testGenerateBitVectorFromArray() {
        // Varargs overload, mirroring testGenerateBitVector (RED=1, AMBER=2, GREEN=4).
        assertEquals(0L, EnumUtils.generateBitVector(Traffic.class));
        assertEquals(1L, EnumUtils.generateBitVector(Traffic.class, Traffic.RED));
        assertEquals(2L, EnumUtils.generateBitVector(Traffic.class, Traffic.AMBER));
        assertEquals(4L, EnumUtils.generateBitVector(Traffic.class, Traffic.GREEN));
        assertEquals(3L, EnumUtils.generateBitVector(Traffic.class, Traffic.RED, Traffic.AMBER));
        assertEquals(5L, EnumUtils.generateBitVector(Traffic.class, Traffic.RED, Traffic.GREEN));
        assertEquals(6L, EnumUtils.generateBitVector(Traffic.class, Traffic.AMBER, Traffic.GREEN));
        assertEquals(7L, EnumUtils.generateBitVector(Traffic.class, Traffic.RED, Traffic.AMBER, Traffic.GREEN));
        // A repeated constant contributes its bit only once.
        assertEquals(7L,
            EnumUtils.generateBitVector(Traffic.class, Traffic.RED, Traffic.AMBER, Traffic.GREEN, Traffic.GREEN));

        // 64-value enum: verify high bit positions survive with no int<->long conversion issue.
        assertEquals(1L << 31, EnumUtils.generateBitVector(Enum64.class, Enum64.A31));
        assertEquals(1L << 32, EnumUtils.generateBitVector(Enum64.class, Enum64.A32));
        assertEquals(1L << 63, EnumUtils.generateBitVector(Enum64.class, Enum64.A63));
        assertEquals(Long.MIN_VALUE, EnumUtils.generateBitVector(Enum64.class, Enum64.A63));
    }

    @Test
    void testGenerateBitVectors() {
        // generateBitVectors returns a long[]; for <=64 constants it holds a single element.
        assertLongArrayEquals(EnumUtils.generateBitVectors(Traffic.class, EnumSet.noneOf(Traffic.class)), new long[] { 0L });
        assertLongArrayEquals(EnumUtils.generateBitVectors(Traffic.class, EnumSet.of(Traffic.RED)), 1L);
        assertLongArrayEquals(EnumUtils.generateBitVectors(Traffic.class, EnumSet.of(Traffic.AMBER)), 2L);
        assertLongArrayEquals(EnumUtils.generateBitVectors(Traffic.class, EnumSet.of(Traffic.GREEN)), 4L);
        assertLongArrayEquals(EnumUtils.generateBitVectors(Traffic.class, EnumSet.of(Traffic.RED, Traffic.AMBER)), 3L);
        assertLongArrayEquals(EnumUtils.generateBitVectors(Traffic.class, EnumSet.of(Traffic.RED, Traffic.GREEN)), 5L);
        assertLongArrayEquals(EnumUtils.generateBitVectors(Traffic.class, EnumSet.of(Traffic.AMBER, Traffic.GREEN)), 6L);
        assertLongArrayEquals(
            EnumUtils.generateBitVectors(Traffic.class, EnumSet.of(Traffic.RED, Traffic.AMBER, Traffic.GREEN)), 7L);

        // 64-value enum: verify high bit positions survive with no int<->long conversion issue.
        assertLongArrayEquals(EnumUtils.generateBitVectors(Enum64.class, EnumSet.of(Enum64.A31)), 1L << 31);
        assertLongArrayEquals(EnumUtils.generateBitVectors(Enum64.class, EnumSet.of(Enum64.A32)), 1L << 32);
        assertLongArrayEquals(EnumUtils.generateBitVectors(Enum64.class, EnumSet.of(Enum64.A63)), 1L << 63);
        assertLongArrayEquals(EnumUtils.generateBitVectors(Enum64.class, EnumSet.of(Enum64.A63)), Long.MIN_VALUE);

        // More than 64 constants: a second long is used, with the least significant block rightmost.
        assertLongArrayEquals(EnumUtils.generateBitVectors(TooMany.class, EnumSet.of(TooMany.M2)), 1L, 0L);
        assertLongArrayEquals(EnumUtils.generateBitVectors(TooMany.class, EnumSet.of(TooMany.L2, TooMany.M2)), 1L,
            1L << 63);
    }

    @SuppressWarnings("unchecked")
    @Test
    void testGenerateBitVectors_nonEnumClass() {
        @SuppressWarnings("rawtypes")
        final Class rawType = Object.class;
        @SuppressWarnings("rawtypes")
        final List rawList = new ArrayList();
        assertIllegalArgumentException(() -> EnumUtils.generateBitVectors(rawType, rawList));
    }

    @SuppressWarnings("unchecked")
    @Test
    void testGenerateBitVectors_nonEnumClassWithArray() {
        @SuppressWarnings("rawtypes")
        final Class rawType = Object.class;
        assertIllegalArgumentException(() -> EnumUtils.generateBitVectors(rawType));
    }

    @Test
    void testGenerateBitVectors_nullArray() {
        assertNullPointerException(() -> EnumUtils.generateBitVectors(Traffic.class, (Traffic[]) null));
    }

    @Test
    void testGenerateBitVectors_nullArrayElement() {
        assertIllegalArgumentException(
            () -> EnumUtils.generateBitVectors(Traffic.class, Traffic.RED, null));
    }

    @Test
    void testGenerateBitVectors_nullClass() {
        assertNullPointerException(() -> EnumUtils.generateBitVectors(null, EnumSet.of(Traffic.RED)));
    }

    @Test
    void testGenerateBitVectors_nullClassWithArray() {
        assertNullPointerException(() -> EnumUtils.generateBitVectors(null, Traffic.RED));
    }

    @Test
    void testGenerateBitVectors_nullElement() {
        assertNullPointerException(
            () -> EnumUtils.generateBitVectors(Traffic.class, Arrays.asList(Traffic.RED, null)));
    }

    @Test
    void testGenerateBitVectors_nullIterable() {
        assertNullPointerException(() -> EnumUtils.generateBitVectors(null, (Iterable<Traffic>) null));
    }

    @Test
    void testGenerateBitVectorsFromArray() {
        // Varargs overload, mirroring testGenerateBitVectors.
        assertLongArrayEquals(EnumUtils.generateBitVectors(Traffic.class), new long[] { 0L });
        assertLongArrayEquals(EnumUtils.generateBitVectors(Traffic.class, Traffic.RED), 1L);
        assertLongArrayEquals(EnumUtils.generateBitVectors(Traffic.class, Traffic.AMBER), 2L);
        assertLongArrayEquals(EnumUtils.generateBitVectors(Traffic.class, Traffic.GREEN), 4L);
        assertLongArrayEquals(EnumUtils.generateBitVectors(Traffic.class, Traffic.RED, Traffic.AMBER), 3L);
        assertLongArrayEquals(EnumUtils.generateBitVectors(Traffic.class, Traffic.RED, Traffic.GREEN), 5L);
        assertLongArrayEquals(EnumUtils.generateBitVectors(Traffic.class, Traffic.AMBER, Traffic.GREEN), 6L);
        assertLongArrayEquals(EnumUtils.generateBitVectors(Traffic.class, Traffic.RED, Traffic.AMBER, Traffic.GREEN), 7L);
        // A repeated constant contributes its bit only once.
        assertLongArrayEquals(
            EnumUtils.generateBitVectors(Traffic.class, Traffic.RED, Traffic.AMBER, Traffic.GREEN, Traffic.GREEN), 7L);

        // 64-value enum: verify high bit positions survive with no int<->long conversion issue.
        assertLongArrayEquals(EnumUtils.generateBitVectors(Enum64.class, Enum64.A31), 1L << 31);
        assertLongArrayEquals(EnumUtils.generateBitVectors(Enum64.class, Enum64.A32), 1L << 32);
        assertLongArrayEquals(EnumUtils.generateBitVectors(Enum64.class, Enum64.A63), 1L << 63);
        assertLongArrayEquals(EnumUtils.generateBitVectors(Enum64.class, Enum64.A63), Long.MIN_VALUE);

        // More than 64 constants: a second long is used, with the least significant block rightmost.
        assertLongArrayEquals(EnumUtils.generateBitVectors(TooMany.class, TooMany.M2), 1L, 0L);
        assertLongArrayEquals(EnumUtils.generateBitVectors(TooMany.class, TooMany.L2, TooMany.M2), 1L, 1L << 63);

    }

    @Test
    void testGetEnum() {
        assertEquals(Traffic.RED, EnumUtils.getEnum(Traffic.class, "RED"));
        assertEquals(Traffic.AMBER, EnumUtils.getEnum(Traffic.class, "AMBER"));
        assertEquals(Traffic.GREEN, EnumUtils.getEnum(Traffic.class, "GREEN"));
        // Unknown name and null name both yield null (no default supplied).
        assertNull(EnumUtils.getEnum(Traffic.class, "PURPLE"));
        assertNull(EnumUtils.getEnum(Traffic.class, null));
    }

    @Test
    void testGetEnum_defaultEnum() {
        // Known names resolve to their constant, ignoring the default.
        assertEquals(Traffic.RED, EnumUtils.getEnum(Traffic.class, "RED", Traffic.AMBER));
        assertEquals(Traffic.AMBER, EnumUtils.getEnum(Traffic.class, "AMBER", Traffic.GREEN));
        assertEquals(Traffic.GREEN, EnumUtils.getEnum(Traffic.class, "GREEN", Traffic.RED));
        // Unknown name falls back to the supplied default.
        assertEquals(Traffic.AMBER, EnumUtils.getEnum(Traffic.class, "PURPLE", Traffic.AMBER));
        assertEquals(Traffic.GREEN, EnumUtils.getEnum(Traffic.class, "PURPLE", Traffic.GREEN));
        assertEquals(Traffic.RED, EnumUtils.getEnum(Traffic.class, "PURPLE", Traffic.RED));
        // Null name falls back to the supplied default.
        assertEquals(Traffic.AMBER, EnumUtils.getEnum(Traffic.class, null, Traffic.AMBER));
        assertEquals(Traffic.GREEN, EnumUtils.getEnum(Traffic.class, null, Traffic.GREEN));
        assertEquals(Traffic.RED, EnumUtils.getEnum(Traffic.class, null, Traffic.RED));
        // A null default is returned as-is when the name is unknown.
        assertNull(EnumUtils.getEnum(Traffic.class, "PURPLE", null));
        // A null class falls back to the supplied default.
        assertEquals(Traffic.AMBER, EnumUtils.getEnum(null, "RED", Traffic.AMBER));
    }

    /**
     * Tests raw type.
     */
    @SuppressWarnings("unchecked")
    @Test
    void testGetEnum_nonEnumClass() {
        @SuppressWarnings("rawtypes")
        final Class rawType = Object.class;
        assertNull(EnumUtils.getEnum(rawType, "rawType"));
    }

    @Test
    void testGetEnum_nullClass() {
        assertNull(EnumUtils.getEnum((Class<Traffic>) null, "PURPLE"));
    }

    @Test
    void testGetEnumIgnoreCase() {
        // Matching is case insensitive.
        assertEquals(Traffic.RED, EnumUtils.getEnumIgnoreCase(Traffic.class, "red"));
        assertEquals(Traffic.AMBER, EnumUtils.getEnumIgnoreCase(Traffic.class, "Amber"));
        assertEquals(Traffic.GREEN, EnumUtils.getEnumIgnoreCase(Traffic.class, "grEEn"));
        // Unknown name and null name both yield null (no default supplied).
        assertNull(EnumUtils.getEnumIgnoreCase(Traffic.class, "purple"));
        assertNull(EnumUtils.getEnumIgnoreCase(Traffic.class, null));
    }

    @Test
    void testGetEnumIgnoreCase_defaultEnum() {
        // Known names (any case) resolve to their constant, ignoring the default.
        assertEquals(Traffic.RED, EnumUtils.getEnumIgnoreCase(Traffic.class, "red", Traffic.AMBER));
        assertEquals(Traffic.AMBER, EnumUtils.getEnumIgnoreCase(Traffic.class, "Amber", Traffic.GREEN));
        assertEquals(Traffic.GREEN, EnumUtils.getEnumIgnoreCase(Traffic.class, "grEEn", Traffic.RED));
        // Unknown name falls back to the supplied default.
        assertEquals(Traffic.AMBER, EnumUtils.getEnumIgnoreCase(Traffic.class, "PURPLE", Traffic.AMBER));
        assertEquals(Traffic.GREEN, EnumUtils.getEnumIgnoreCase(Traffic.class, "purple", Traffic.GREEN));
        assertEquals(Traffic.RED, EnumUtils.getEnumIgnoreCase(Traffic.class, "pUrPlE", Traffic.RED));
        // Null name falls back to the supplied default.
        assertEquals(Traffic.AMBER, EnumUtils.getEnumIgnoreCase(Traffic.class, null, Traffic.AMBER));
        assertEquals(Traffic.GREEN, EnumUtils.getEnumIgnoreCase(Traffic.class, null, Traffic.GREEN));
        assertEquals(Traffic.RED, EnumUtils.getEnumIgnoreCase(Traffic.class, null, Traffic.RED));
        // A null default is returned as-is when the name is unknown.
        assertNull(EnumUtils.getEnumIgnoreCase(Traffic.class, "PURPLE", null));
        // A null class returns the (null) default.
        assertNull(EnumUtils.getEnumIgnoreCase(null, "PURPLE", null));
    }

    /**
     * Tests raw type.
     */
    @SuppressWarnings("unchecked")
    @Test
    void testGetEnumIgnoreCase_nonEnumClass() {
        @SuppressWarnings("rawtypes")
        final Class rawType = Object.class;
        assertNull(EnumUtils.getEnumIgnoreCase(rawType, "rawType"));
    }

    @Test
    void testGetEnumIgnoreCase_nullClass() {
        assertNull(EnumUtils.getEnumIgnoreCase((Class<Traffic>) null, "PURPLE"));
    }

    @Test
    void testGetEnumList() {
        // The list preserves declaration order.
        final List<Traffic> test = EnumUtils.getEnumList(Traffic.class);
        assertEquals(3, test.size());
        assertEquals(Traffic.RED, test.get(0));
        assertEquals(Traffic.AMBER, test.get(1));
        assertEquals(Traffic.GREEN, test.get(2));
    }

    @Test
    void testGetEnumMap() {
        // Default key function maps each constant by its name().
        final Map<String, Traffic> test = EnumUtils.getEnumMap(Traffic.class);
        final Map<String, Traffic> expected = new HashMap<>();
        expected.put("RED", Traffic.RED);
        expected.put("AMBER", Traffic.AMBER);
        expected.put("GREEN", Traffic.GREEN);
        assertEquals(expected, test, "getEnumMap not created correctly");
        assertEquals(3, test.size());
        assertTrue(test.containsKey("RED"));
        assertEquals(Traffic.RED, test.get("RED"));
        assertTrue(test.containsKey("AMBER"));
        assertEquals(Traffic.AMBER, test.get("AMBER"));
        assertTrue(test.containsKey("GREEN"));
        assertEquals(Traffic.GREEN, test.get("GREEN"));
        assertFalse(test.containsKey("PURPLE"));
    }

    @Test
    void testGetEnumMap_keyFunction() {
        // Custom key function maps each Month by its numeric id (1..12).
        final Map<Integer, Month> test = EnumUtils.getEnumMap(Month.class, Month::getId);
        final Map<Integer, Month> expected = new HashMap<>();
        expected.put(1, Month.JAN);
        expected.put(2, Month.FEB);
        expected.put(3, Month.MAR);
        expected.put(4, Month.APR);
        expected.put(5, Month.MAY);
        expected.put(6, Month.JUN);
        expected.put(7, Month.JUL);
        expected.put(8, Month.AUG);
        expected.put(9, Month.SEP);
        expected.put(10, Month.OCT);
        expected.put(11, Month.NOV);
        expected.put(12, Month.DEC);
        assertEquals(expected, test, "getEnumMap not created correctly");
        assertEquals(12, test.size());
        // Ids run from 1 to 12; 0 and 13 are out of range.
        assertFalse(test.containsKey(0));
        assertTrue(test.containsKey(1));
        assertEquals(Month.JAN, test.get(1));
        assertTrue(test.containsKey(2));
        assertEquals(Month.FEB, test.get(2));
        assertTrue(test.containsKey(3));
        assertEquals(Month.MAR, test.get(3));
        assertTrue(test.containsKey(4));
        assertEquals(Month.APR, test.get(4));
        assertTrue(test.containsKey(5));
        assertEquals(Month.MAY, test.get(5));
        assertTrue(test.containsKey(6));
        assertEquals(Month.JUN, test.get(6));
        assertTrue(test.containsKey(7));
        assertEquals(Month.JUL, test.get(7));
        assertTrue(test.containsKey(8));
        assertEquals(Month.AUG, test.get(8));
        assertTrue(test.containsKey(9));
        assertEquals(Month.SEP, test.get(9));
        assertTrue(test.containsKey(10));
        assertEquals(Month.OCT, test.get(10));
        assertTrue(test.containsKey(11));
        assertEquals(Month.NOV, test.get(11));
        assertTrue(test.containsKey(12));
        assertEquals(Month.DEC, test.get(12));
        assertFalse(test.containsKey(13));
    }

    @Test
    void testGetEnumSystemProperty() {
        // Use this test's own class name as a throwaway system property key.
        final String key = getClass().getName();
        System.setProperty(key, Traffic.RED.toString());
        try {
            // Property is set to "RED": resolves to Traffic.RED regardless of the default.
            assertEquals(Traffic.RED, EnumUtils.getEnumSystemProperty(Traffic.class, key, null));
            // Unknown/absent property key "?" falls back to the default.
            assertEquals(Traffic.RED, EnumUtils.getEnumSystemProperty(Traffic.class, "?", Traffic.RED));
            assertEquals(Traffic.RED, EnumUtils.getEnumSystemProperty(null, null, Traffic.RED));
            assertEquals(Traffic.RED, EnumUtils.getEnumSystemProperty(null, "?", Traffic.RED));
            assertEquals(Traffic.RED, EnumUtils.getEnumSystemProperty(Traffic.class, null, Traffic.RED));
        } finally {
            // Always clean up the system property.
            System.getProperties().remove(key);
        }
    }

    @Test
    void testGetFirstEnumIgnoreCase_defaultEnum() {
        // Match Traffic2 constants by their label (e.g. "***Red***"), case insensitively.
        final Function<Traffic2, String> f = Traffic2::getLabel;
        assertEquals(Traffic2.RED, EnumUtils.getFirstEnumIgnoreCase(Traffic2.class, "***red***", f, Traffic2.AMBER));
        assertEquals(Traffic2.AMBER, EnumUtils.getFirstEnumIgnoreCase(Traffic2.class, "**Amber**", f, Traffic2.GREEN));
        assertEquals(Traffic2.GREEN, EnumUtils.getFirstEnumIgnoreCase(Traffic2.class, "*grEEn*", f, Traffic2.RED));
        // No label matches "PURPLE": fall back to the default.
        assertEquals(Traffic2.AMBER, EnumUtils.getFirstEnumIgnoreCase(Traffic2.class, "PURPLE", f, Traffic2.AMBER));
        assertEquals(Traffic2.GREEN, EnumUtils.getFirstEnumIgnoreCase(Traffic2.class, "purple", f, Traffic2.GREEN));
        assertEquals(Traffic2.RED, EnumUtils.getFirstEnumIgnoreCase(Traffic2.class, "pUrPlE", f, Traffic2.RED));
        // Null name falls back to the default.
        assertEquals(Traffic2.AMBER, EnumUtils.getFirstEnumIgnoreCase(Traffic2.class, null, f, Traffic2.AMBER));
        assertEquals(Traffic2.GREEN, EnumUtils.getFirstEnumIgnoreCase(Traffic2.class, null, f, Traffic2.GREEN));
        assertEquals(Traffic2.RED, EnumUtils.getFirstEnumIgnoreCase(Traffic2.class, null, f, Traffic2.RED));
        // A null default / null class are returned as the (null) default.
        assertNull(EnumUtils.getFirstEnumIgnoreCase(Traffic2.class, "PURPLE", f, null));
        assertNull(EnumUtils.getFirstEnumIgnoreCase(null, "PURPLE", f, null));
    }

    @Test
    void testGetFirstEnumToIntFunction() {
        // Match Traffic2 constants by their int value (RED=1, AMBER=2, GREEN=3).
        final ToIntFunction<Traffic2> f = Traffic2::getValue;
        assertEquals(Traffic2.RED, EnumUtils.getFirstEnum(Traffic2.class, 1, f, Traffic2.AMBER));
        assertEquals(Traffic2.AMBER, EnumUtils.getFirstEnum(Traffic2.class, 2, f, Traffic2.GREEN));
        assertEquals(Traffic2.GREEN, EnumUtils.getFirstEnum(Traffic2.class, 3, f, Traffic2.RED));
        // No value matches 4, 5, 6 or 0: fall back to the default.
        assertEquals(Traffic2.AMBER, EnumUtils.getFirstEnum(Traffic2.class, 4, f, Traffic2.AMBER));
        assertEquals(Traffic2.GREEN, EnumUtils.getFirstEnum(Traffic2.class, 5, f, Traffic2.GREEN));
        assertEquals(Traffic2.RED, EnumUtils.getFirstEnum(Traffic2.class, 6, f, Traffic2.RED));
        assertEquals(Traffic2.AMBER, EnumUtils.getFirstEnum(Traffic2.class, 0, f, Traffic2.AMBER));
        assertEquals(Traffic2.GREEN, EnumUtils.getFirstEnum(Traffic2.class, -1, f, Traffic2.GREEN));
        assertEquals(Traffic2.RED, EnumUtils.getFirstEnum(Traffic2.class, 0, f, Traffic2.RED));
        // A null default with no match returns null.
        assertNull(EnumUtils.getFirstEnum(Traffic2.class, 7, f, null));
        // Edge cases for the 1st argument: null class and a non-enum class fall back to the default.
        assertEquals(Traffic2.AMBER, EnumUtils.getFirstEnum(null, 1, f, Traffic2.AMBER));
        assertEquals(Traffic2.AMBER, EnumUtils.getFirstEnum((Class) String.class, 1, f, Traffic2.AMBER));
    }

    @Test
    void testIsValidEnum() {
        assertTrue(EnumUtils.isValidEnum(Traffic.class, "RED"));
        assertTrue(EnumUtils.isValidEnum(Traffic.class, "AMBER"));
        assertTrue(EnumUtils.isValidEnum(Traffic.class, "GREEN"));
        // Unknown name and null name are not valid.
        assertFalse(EnumUtils.isValidEnum(Traffic.class, "PURPLE"));
        assertFalse(EnumUtils.isValidEnum(Traffic.class, null));
    }

    @Test
    void testIsValidEnum_nullClass() {
        assertFalse(EnumUtils.isValidEnum(null, "PURPLE"));
    }

    @Test
    void testIsValidEnumIgnoreCase() {
        // Validation is case insensitive.
        assertTrue(EnumUtils.isValidEnumIgnoreCase(Traffic.class, "red"));
        assertTrue(EnumUtils.isValidEnumIgnoreCase(Traffic.class, "Amber"));
        assertTrue(EnumUtils.isValidEnumIgnoreCase(Traffic.class, "grEEn"));
        // Unknown name and null name are not valid.
        assertFalse(EnumUtils.isValidEnumIgnoreCase(Traffic.class, "purple"));
        assertFalse(EnumUtils.isValidEnumIgnoreCase(Traffic.class, null));
    }

    @Test
    void testIsValidEnumIgnoreCase_nullClass() {
        assertFalse(EnumUtils.isValidEnumIgnoreCase(null, "PURPLE"));
    }

    @Test
    void testProcessBitVector() {
        // processBitVector is the inverse of generateBitVector (RED=1, AMBER=2, GREEN=4).
        assertEquals(EnumSet.noneOf(Traffic.class), EnumUtils.processBitVector(Traffic.class, 0L));
        assertEquals(EnumSet.of(Traffic.RED), EnumUtils.processBitVector(Traffic.class, 1L));
        assertEquals(EnumSet.of(Traffic.AMBER), EnumUtils.processBitVector(Traffic.class, 2L));
        assertEquals(EnumSet.of(Traffic.RED, Traffic.AMBER), EnumUtils.processBitVector(Traffic.class, 3L));
        assertEquals(EnumSet.of(Traffic.GREEN), EnumUtils.processBitVector(Traffic.class, 4L));
        assertEquals(EnumSet.of(Traffic.RED, Traffic.GREEN), EnumUtils.processBitVector(Traffic.class, 5L));
        assertEquals(EnumSet.of(Traffic.AMBER, Traffic.GREEN), EnumUtils.processBitVector(Traffic.class, 6L));
        assertEquals(EnumSet.of(Traffic.RED, Traffic.AMBER, Traffic.GREEN),
            EnumUtils.processBitVector(Traffic.class, 7L));

        // 64-value enum: verify high bit positions survive with no int<->long conversion issue.
        assertEquals(EnumSet.of(Enum64.A31), EnumUtils.processBitVector(Enum64.class, 1L << 31));
        assertEquals(EnumSet.of(Enum64.A32), EnumUtils.processBitVector(Enum64.class, 1L << 32));
        assertEquals(EnumSet.of(Enum64.A63), EnumUtils.processBitVector(Enum64.class, 1L << 63));
        assertEquals(EnumSet.of(Enum64.A63), EnumUtils.processBitVector(Enum64.class, Long.MIN_VALUE));
    }

    @Test
    void testProcessBitVector_longClass() {
        // An enum with more than 64 constants cannot be processed from a single long.
        assertIllegalArgumentException(() -> EnumUtils.processBitVector(TooMany.class, 0L));
    }

    @Test
    void testProcessBitVector_nullClass() {
        final Class<Traffic> empty = null;
        assertNullPointerException(() -> EnumUtils.processBitVector(empty, 0L));
    }

    @Test
    void testProcessBitVectors() {
        // Single-long form: inverse of generateBitVectors (RED=1, AMBER=2, GREEN=4).
        assertEquals(EnumSet.noneOf(Traffic.class), EnumUtils.processBitVectors(Traffic.class, 0L));
        assertEquals(EnumSet.of(Traffic.RED), EnumUtils.processBitVectors(Traffic.class, 1L));
        assertEquals(EnumSet.of(Traffic.AMBER), EnumUtils.processBitVectors(Traffic.class, 2L));
        assertEquals(EnumSet.of(Traffic.RED, Traffic.AMBER), EnumUtils.processBitVectors(Traffic.class, 3L));
        assertEquals(EnumSet.of(Traffic.GREEN), EnumUtils.processBitVectors(Traffic.class, 4L));
        assertEquals(EnumSet.of(Traffic.RED, Traffic.GREEN), EnumUtils.processBitVectors(Traffic.class, 5L));
        assertEquals(EnumSet.of(Traffic.AMBER, Traffic.GREEN), EnumUtils.processBitVectors(Traffic.class, 6L));
        assertEquals(EnumSet.of(Traffic.RED, Traffic.AMBER, Traffic.GREEN),
            EnumUtils.processBitVectors(Traffic.class, 7L));

        // Two longs supplied: only the rightmost (least significant) block is relevant for Traffic.
        assertEquals(EnumSet.noneOf(Traffic.class), EnumUtils.processBitVectors(Traffic.class, 0L, 0L));
        assertEquals(EnumSet.of(Traffic.RED), EnumUtils.processBitVectors(Traffic.class, 0L, 1L));
        assertEquals(EnumSet.of(Traffic.AMBER), EnumUtils.processBitVectors(Traffic.class, 0L, 2L));
        assertEquals(EnumSet.of(Traffic.RED, Traffic.AMBER), EnumUtils.processBitVectors(Traffic.class, 0L, 3L));
        assertEquals(EnumSet.of(Traffic.GREEN), EnumUtils.processBitVectors(Traffic.class, 0L, 4L));
        assertEquals(EnumSet.of(Traffic.RED, Traffic.GREEN), EnumUtils.processBitVectors(Traffic.class, 0L, 5L));
        assertEquals(EnumSet.of(Traffic.AMBER, Traffic.GREEN), EnumUtils.processBitVectors(Traffic.class, 0L, 6L));
        assertEquals(EnumSet.of(Traffic.RED, Traffic.AMBER, Traffic.GREEN),
            EnumUtils.processBitVectors(Traffic.class, 0L, 7L));

        // High-order block (here 666L) is irrelevant for a 3-constant enum and is ignored.
        assertEquals(EnumSet.noneOf(Traffic.class), EnumUtils.processBitVectors(Traffic.class, 666L, 0L));
        assertEquals(EnumSet.of(Traffic.RED), EnumUtils.processBitVectors(Traffic.class, 666L, 1L));
        assertEquals(EnumSet.of(Traffic.AMBER), EnumUtils.processBitVectors(Traffic.class, 666L, 2L));
        assertEquals(EnumSet.of(Traffic.RED, Traffic.AMBER), EnumUtils.processBitVectors(Traffic.class, 666L, 3L));
        assertEquals(EnumSet.of(Traffic.GREEN), EnumUtils.processBitVectors(Traffic.class, 666L, 4L));
        assertEquals(EnumSet.of(Traffic.RED, Traffic.GREEN), EnumUtils.processBitVectors(Traffic.class, 666L, 5L));
        assertEquals(EnumSet.of(Traffic.AMBER, Traffic.GREEN), EnumUtils.processBitVectors(Traffic.class, 666L, 6L));
        assertEquals(EnumSet.of(Traffic.RED, Traffic.AMBER, Traffic.GREEN),
            EnumUtils.processBitVectors(Traffic.class, 666L, 7L));

        // 64-value enum: verify high bit positions survive with no int<->long conversion issue.
        assertEquals(EnumSet.of(Enum64.A31), EnumUtils.processBitVectors(Enum64.class, 1L << 31));
        assertEquals(EnumSet.of(Enum64.A32), EnumUtils.processBitVectors(Enum64.class, 1L << 32));
        assertEquals(EnumSet.of(Enum64.A63), EnumUtils.processBitVectors(Enum64.class, 1L << 63));
        assertEquals(EnumSet.of(Enum64.A63), EnumUtils.processBitVectors(Enum64.class, Long.MIN_VALUE));
    }

    @Test
    void testProcessBitVectors_longClass() {
        // TooMany has >64 constants; its first 64 (A..Z, A1.. up to A2) live in the rightmost block.
        assertEquals(EnumSet.noneOf(TooMany.class), EnumUtils.processBitVectors(TooMany.class, 0L));
        assertEquals(EnumSet.of(TooMany.A), EnumUtils.processBitVectors(TooMany.class, 1L));
        assertEquals(EnumSet.of(TooMany.B), EnumUtils.processBitVectors(TooMany.class, 2L));
        assertEquals(EnumSet.of(TooMany.A, TooMany.B), EnumUtils.processBitVectors(TooMany.class, 3L));
        assertEquals(EnumSet.of(TooMany.C), EnumUtils.processBitVectors(TooMany.class, 4L));
        assertEquals(EnumSet.of(TooMany.A, TooMany.C), EnumUtils.processBitVectors(TooMany.class, 5L));
        assertEquals(EnumSet.of(TooMany.B, TooMany.C), EnumUtils.processBitVectors(TooMany.class, 6L));
        assertEquals(EnumSet.of(TooMany.A, TooMany.B, TooMany.C), EnumUtils.processBitVectors(TooMany.class, 7L));

        // A leading 0L block leaves the low constants unchanged.
        assertEquals(EnumSet.noneOf(TooMany.class), EnumUtils.processBitVectors(TooMany.class, 0L, 0L));
        assertEquals(EnumSet.of(TooMany.A), EnumUtils.processBitVectors(TooMany.class, 0L, 1L));
        assertEquals(EnumSet.of(TooMany.B), EnumUtils.processBitVectors(TooMany.class, 0L, 2L));
        assertEquals(EnumSet.of(TooMany.A, TooMany.B), EnumUtils.processBitVectors(TooMany.class, 0L, 3L));
        assertEquals(EnumSet.of(TooMany.C), EnumUtils.processBitVectors(TooMany.class, 0L, 4L));
        assertEquals(EnumSet.of(TooMany.A, TooMany.C), EnumUtils.processBitVectors(TooMany.class, 0L, 5L));
        assertEquals(EnumSet.of(TooMany.B, TooMany.C), EnumUtils.processBitVectors(TooMany.class, 0L, 6L));
        assertEquals(EnumSet.of(TooMany.A, TooMany.B, TooMany.C), EnumUtils.processBitVectors(TooMany.class, 0L, 7L));
        assertEquals(EnumSet.of(TooMany.A, TooMany.B, TooMany.C), EnumUtils.processBitVectors(TooMany.class, 0L, 7L));

        // A leading 1L block (bit 0 of the high block) selects the 65th constant, M2.
        assertEquals(EnumSet.of(TooMany.M2), EnumUtils.processBitVectors(TooMany.class, 1L, 0L));
        assertEquals(EnumSet.of(TooMany.A, TooMany.M2), EnumUtils.processBitVectors(TooMany.class, 1L, 1L));
        assertEquals(EnumSet.of(TooMany.B, TooMany.M2), EnumUtils.processBitVectors(TooMany.class, 1L, 2L));
        assertEquals(EnumSet.of(TooMany.A, TooMany.B, TooMany.M2), EnumUtils.processBitVectors(TooMany.class, 1L, 3L));
        assertEquals(EnumSet.of(TooMany.C, TooMany.M2), EnumUtils.processBitVectors(TooMany.class, 1L, 4L));
        assertEquals(EnumSet.of(TooMany.A, TooMany.C, TooMany.M2), EnumUtils.processBitVectors(TooMany.class, 1L, 5L));
        assertEquals(EnumSet.of(TooMany.B, TooMany.C, TooMany.M2), EnumUtils.processBitVectors(TooMany.class, 1L, 6L));
        assertEquals(EnumSet.of(TooMany.A, TooMany.B, TooMany.C, TooMany.M2),
            EnumUtils.processBitVectors(TooMany.class, 1L, 7L));
        assertEquals(EnumSet.of(TooMany.A, TooMany.B, TooMany.C, TooMany.M2),
            EnumUtils.processBitVectors(TooMany.class, 1L, 7L));

        // High block 9L still only sets bit 0 (M2) for this enum; its other bits are irrelevant.
        assertEquals(EnumSet.of(TooMany.M2), EnumUtils.processBitVectors(TooMany.class, 9L, 0L));
        assertEquals(EnumSet.of(TooMany.A, TooMany.M2), EnumUtils.processBitVectors(TooMany.class, 9L, 1L));
        assertEquals(EnumSet.of(TooMany.B, TooMany.M2), EnumUtils.processBitVectors(TooMany.class, 9L, 2L));
        assertEquals(EnumSet.of(TooMany.A, TooMany.B, TooMany.M2), EnumUtils.processBitVectors(TooMany.class, 9L, 3L));
        assertEquals(EnumSet.of(TooMany.C, TooMany.M2), EnumUtils.processBitVectors(TooMany.class, 9L, 4L));
        assertEquals(EnumSet.of(TooMany.A, TooMany.C, TooMany.M2), EnumUtils.processBitVectors(TooMany.class, 9L, 5L));
        assertEquals(EnumSet.of(TooMany.B, TooMany.C, TooMany.M2), EnumUtils.processBitVectors(TooMany.class, 9L, 6L));
        assertEquals(EnumSet.of(TooMany.A, TooMany.B, TooMany.C, TooMany.M2),
            EnumUtils.processBitVectors(TooMany.class, 9L, 7L));
        assertEquals(EnumSet.of(TooMany.A, TooMany.B, TooMany.C, TooMany.M2),
            EnumUtils.processBitVectors(TooMany.class, 9L, 7L));
    }

    @Test
    void testProcessBitVectors_nullClass() {
        final Class<Traffic> empty = null;
        assertNullPointerException(() -> EnumUtils.processBitVectors(empty, 0L));
    }

    @Test
    void testStream() {
        // A real enum streams its constants; a non-enum or null class streams nothing.
        assertEquals(7, EnumUtils.stream(TimeUnit.class).count());
        assertArrayEquals(TimeUnit.values(), EnumUtils.stream(TimeUnit.class).toArray(TimeUnit[]::new));
        assertEquals(0, EnumUtils.stream(Object.class).count());
        assertEquals(0, EnumUtils.stream(null).count());
    }

}

/**
 * An enum whose constants carry a 1-based numeric id, used to test
 * {@link EnumUtils#getEnumMap(Class, java.util.function.Function)} with a custom key function.
 */
enum Month {
    JAN(1), FEB(2), MAR(3), APR(4), MAY(5), JUN(6), JUL(7), AUG(8), SEP(9), OCT(10), NOV(11), DEC(12);

    private final int id;

    Month(final int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }
}

/**
 * An enum with 65 constants (one more than fits in a single {@code long}), used to test
 * the multi-{@code long} bit vector paths. {@code M2} is the 65th constant (ordinal 64),
 * so it is the first to spill into a second {@code long}.
 */
enum TooMany {
    A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z, A1, B1, C1, D1, E1, F1, G1, H1, I1,
    J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1, U1, V1, W1, X1, Y1, Z1, A2, B2, C2, D2, E2, F2, G2, H2, I2, J2, K2, L2,
    M2
}

/**
 * A simple three-constant enum (a traffic light) whose ordinals drive the single-{@code long}
 * bit vector tests: {@code RED}=0, {@code AMBER}=1, {@code GREEN}=2.
 */
enum Traffic {
    RED, AMBER, GREEN
}

/**
 * A traffic-light enum that additionally carries a display {@code label} and an int {@code value},
 * used to test lookups by a custom string function and by a custom int function.
 */
enum Traffic2 {

    RED("***Red***", 1), AMBER("**Amber**", 2), GREEN("*green*", 3);

    final String label;
    final int value;

    Traffic2(final String label, final int value) {
        this.label = label;
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public int getValue() {
        return value;
    }
}
