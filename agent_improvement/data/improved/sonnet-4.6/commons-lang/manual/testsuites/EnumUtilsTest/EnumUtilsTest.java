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

enum Enum64 {
    A00, A01, A02, A03, A04, A05, A06, A07, A08, A09, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21, A22,
    A23, A24, A25, A26, A27, A28, A29, A30, A31, A32, A33, A34, A35, A36, A37, A38, A39, A40, A41, A42, A43, A44, A45,
    A46, A47, A48, A49, A50, A51, A52, A53, A54, A55, A56, A57, A58, A59, A60, A61, A62, A63
}

/**
 * Tests for {@link EnumUtils}.
 *
 * <p>The {@link Traffic} enum used throughout has three values with ordinals:
 * RED=0 (bit 0, mask 1), AMBER=1 (bit 1, mask 2), GREEN=2 (bit 2, mask 4).
 * Combined masks follow standard bitwise OR rules.</p>
 */
class EnumUtilsTest extends AbstractLangTest {

    /** Delegates to assertArrayEquals with swapped argument order to match our call-site convention. */
    private void assertLongArrayEquals(final long[] actual, final long... expected) {
        assertArrayEquals(expected, (long[]) actual);
    }

    @Test
    void testConstructable() {
        // enforce public constructor
        new EnumUtils();
    }

    // -----------------------------------------------------------------------
    // generateBitVector (Iterable overload)
    // -----------------------------------------------------------------------

    /**
     * Verifies that each Traffic value maps to its expected ordinal bit mask,
     * and that combined sets produce the correct OR of those masks.
     * Traffic bit positions: RED=bit0 (1), AMBER=bit1 (2), GREEN=bit2 (4).
     */
    @Test
    void testGenerateBitVector() {
        // empty set
        assertEquals(0L, EnumUtils.generateBitVector(Traffic.class, EnumSet.noneOf(Traffic.class)));

        // single values
        assertEquals(1L, EnumUtils.generateBitVector(Traffic.class, EnumSet.of(Traffic.RED)));
        assertEquals(2L, EnumUtils.generateBitVector(Traffic.class, EnumSet.of(Traffic.AMBER)));
        assertEquals(4L, EnumUtils.generateBitVector(Traffic.class, EnumSet.of(Traffic.GREEN)));

        // pairs
        assertEquals(3L, EnumUtils.generateBitVector(Traffic.class, EnumSet.of(Traffic.RED, Traffic.AMBER)));
        assertEquals(5L, EnumUtils.generateBitVector(Traffic.class, EnumSet.of(Traffic.RED, Traffic.GREEN)));
        assertEquals(6L, EnumUtils.generateBitVector(Traffic.class, EnumSet.of(Traffic.AMBER, Traffic.GREEN)));

        // all three
        assertEquals(7L,
            EnumUtils.generateBitVector(Traffic.class, EnumSet.of(Traffic.RED, Traffic.AMBER, Traffic.GREEN)));

        // 64-value enum: verify no int<->long conversion issue near the 32-/63-bit boundary
        assertEquals(1L << 31, EnumUtils.generateBitVector(Enum64.class, EnumSet.of(Enum64.A31)));
        assertEquals(1L << 32, EnumUtils.generateBitVector(Enum64.class, EnumSet.of(Enum64.A32)));
        assertEquals(1L << 63, EnumUtils.generateBitVector(Enum64.class, EnumSet.of(Enum64.A63)));
        // 1L<<63 == Long.MIN_VALUE (sign bit); both representations must be equal
        assertEquals(Long.MIN_VALUE, EnumUtils.generateBitVector(Enum64.class, EnumSet.of(Enum64.A63)));
    }

    /** Enums with more than 64 values must not be used with the single-long overload. */
    @Test
    void testGenerateBitVector_longClass() {
        assertIllegalArgumentException(
            () -> EnumUtils.generateBitVector(TooMany.class, EnumSet.of(TooMany.A1)));
    }

    /** Array-varargs overload also rejects enums with more than 64 values. */
    @Test
    void testGenerateBitVector_longClassWithArray() {
        assertIllegalArgumentException(() -> EnumUtils.generateBitVector(TooMany.class, TooMany.A1));
    }

    /** A non-enum class passed as the raw type should throw IllegalArgumentException. */
    @SuppressWarnings("unchecked")
    @Test
    void testGenerateBitVector_nonEnumClass() {
        @SuppressWarnings("rawtypes")
        final Class rawType = Object.class;
        @SuppressWarnings("rawtypes")
        final List rawList = new ArrayList();
        assertIllegalArgumentException(() -> EnumUtils.generateBitVector(rawType, rawList));
    }

    /** Array-varargs overload also rejects non-enum class. */
    @SuppressWarnings("unchecked")
    @Test
    void testGenerateBitVector_nonEnumClassWithArray() {
        @SuppressWarnings("rawtypes")
        final Class rawType = Object.class;
        assertIllegalArgumentException(() -> EnumUtils.generateBitVector(rawType));
    }

    /** A null values array must throw NullPointerException. */
    @Test
    void testGenerateBitVector_nullArray() {
        assertNullPointerException(() -> EnumUtils.generateBitVector(Traffic.class, (Traffic[]) null));
    }

    /** A null element inside the values array must throw IllegalArgumentException. */
    @Test
    void testGenerateBitVector_nullArrayElement() {
        assertIllegalArgumentException(
            () -> EnumUtils.generateBitVector(Traffic.class, Traffic.RED, null));
    }

    /** A null enumClass must throw NullPointerException (Iterable overload). */
    @Test
    void testGenerateBitVector_nullClass() {
        assertNullPointerException(() -> EnumUtils.generateBitVector(null, EnumSet.of(Traffic.RED)));
    }

    /** A null enumClass must throw NullPointerException (array-varargs overload). */
    @Test
    void testGenerateBitVector_nullClassWithArray() {
        assertNullPointerException(() -> EnumUtils.generateBitVector(null, Traffic.RED));
    }

    /** A null element inside an Iterable must throw NullPointerException. */
    @Test
    void testGenerateBitVector_nullElement() {
        assertNullPointerException(
            () -> EnumUtils.generateBitVector(Traffic.class, Arrays.asList(Traffic.RED, null)));
    }

    /** A null Iterable argument must throw NullPointerException. */
    @Test
    void testGenerateBitVector_nullIterable() {
        assertNullPointerException(
            () -> EnumUtils.generateBitVector(Traffic.class, (Iterable<Traffic>) null));
    }

    // -----------------------------------------------------------------------
    // generateBitVector (array/varargs overload)
    // -----------------------------------------------------------------------

    /**
     * Same bit-mask expectations as the Iterable overload; also verifies that
     * duplicate entries are treated idempotently (do not set the same bit twice).
     */
    @Test
    void testGenerateBitVectorFromArray() {
        // empty varargs
        assertEquals(0L, EnumUtils.generateBitVector(Traffic.class));

        // single values
        assertEquals(1L, EnumUtils.generateBitVector(Traffic.class, Traffic.RED));
        assertEquals(2L, EnumUtils.generateBitVector(Traffic.class, Traffic.AMBER));
        assertEquals(4L, EnumUtils.generateBitVector(Traffic.class, Traffic.GREEN));

        // pairs
        assertEquals(3L, EnumUtils.generateBitVector(Traffic.class, Traffic.RED, Traffic.AMBER));
        assertEquals(5L, EnumUtils.generateBitVector(Traffic.class, Traffic.RED, Traffic.GREEN));
        assertEquals(6L, EnumUtils.generateBitVector(Traffic.class, Traffic.AMBER, Traffic.GREEN));

        // all three
        assertEquals(7L, EnumUtils.generateBitVector(Traffic.class, Traffic.RED, Traffic.AMBER, Traffic.GREEN));

        // duplicates: GREEN appears twice but the mask should still be 7
        assertEquals(7L,
            EnumUtils.generateBitVector(Traffic.class, Traffic.RED, Traffic.AMBER, Traffic.GREEN, Traffic.GREEN));

        // 64-value enum: verify no int<->long conversion issue near the 32-/63-bit boundary
        assertEquals(1L << 31, EnumUtils.generateBitVector(Enum64.class, Enum64.A31));
        assertEquals(1L << 32, EnumUtils.generateBitVector(Enum64.class, Enum64.A32));
        assertEquals(1L << 63, EnumUtils.generateBitVector(Enum64.class, Enum64.A63));
        assertEquals(Long.MIN_VALUE, EnumUtils.generateBitVector(Enum64.class, Enum64.A63));
    }

    // -----------------------------------------------------------------------
    // generateBitVectors (multi-long Iterable overload)
    // -----------------------------------------------------------------------

    /**
     * Verifies bit-mask values for the multi-long form, which supports enums
     * larger than 64 values by returning multiple longs (most-significant first).
     * For Traffic (3 values, fits in one long) the result is a single-element array.
     * For TooMany (65 values) it uses two longs; M2 is ordinal 64, mapping to bit 0
     * of the high long, so result[0]=1L and result[1]=0L.
     */
    @Test
    void testGenerateBitVectors() {
        // empty set → single-element array with 0
        assertLongArrayEquals(EnumUtils.generateBitVectors(Traffic.class, EnumSet.noneOf(Traffic.class)), new long[] { 0L });

        // single Traffic values
        assertLongArrayEquals(EnumUtils.generateBitVectors(Traffic.class, EnumSet.of(Traffic.RED)), 1L);
        assertLongArrayEquals(EnumUtils.generateBitVectors(Traffic.class, EnumSet.of(Traffic.AMBER)), 2L);
        assertLongArrayEquals(EnumUtils.generateBitVectors(Traffic.class, EnumSet.of(Traffic.GREEN)), 4L);

        // pairs and all-three
        assertLongArrayEquals(EnumUtils.generateBitVectors(Traffic.class, EnumSet.of(Traffic.RED, Traffic.AMBER)), 3L);
        assertLongArrayEquals(EnumUtils.generateBitVectors(Traffic.class, EnumSet.of(Traffic.RED, Traffic.GREEN)), 5L);
        assertLongArrayEquals(EnumUtils.generateBitVectors(Traffic.class, EnumSet.of(Traffic.AMBER, Traffic.GREEN)), 6L);
        assertLongArrayEquals(
            EnumUtils.generateBitVectors(Traffic.class, EnumSet.of(Traffic.RED, Traffic.AMBER, Traffic.GREEN)), 7L);

        // 64-value enum: verify no int<->long conversion issue near the 32-/63-bit boundary
        assertLongArrayEquals(EnumUtils.generateBitVectors(Enum64.class, EnumSet.of(Enum64.A31)), 1L << 31);
        assertLongArrayEquals(EnumUtils.generateBitVectors(Enum64.class, EnumSet.of(Enum64.A32)), 1L << 32);
        assertLongArrayEquals(EnumUtils.generateBitVectors(Enum64.class, EnumSet.of(Enum64.A63)), 1L << 63);
        assertLongArrayEquals(EnumUtils.generateBitVectors(Enum64.class, EnumSet.of(Enum64.A63)), Long.MIN_VALUE);

        // TooMany has 65 values; M2 (ordinal 64) overflows one long, so it lives in
        // result[0] (bit 0 of the high word) while result[1] (the low word) is 0.
        assertLongArrayEquals(EnumUtils.generateBitVectors(TooMany.class, EnumSet.of(TooMany.M2)), 1L, 0L);
        // L2 (ordinal 63) sits in result[1] as bit 63 (Long.MIN_VALUE = 1L<<63).
        assertLongArrayEquals(EnumUtils.generateBitVectors(TooMany.class, EnumSet.of(TooMany.L2, TooMany.M2)), 1L,
            1L << 63);
    }

    /** Non-enum class (Iterable overload) must throw IllegalArgumentException. */
    @SuppressWarnings("unchecked")
    @Test
    void testGenerateBitVectors_nonEnumClass() {
        @SuppressWarnings("rawtypes")
        final Class rawType = Object.class;
        @SuppressWarnings("rawtypes")
        final List rawList = new ArrayList();
        assertIllegalArgumentException(() -> EnumUtils.generateBitVectors(rawType, rawList));
    }

    /** Non-enum class (array-varargs overload) must throw IllegalArgumentException. */
    @SuppressWarnings("unchecked")
    @Test
    void testGenerateBitVectors_nonEnumClassWithArray() {
        @SuppressWarnings("rawtypes")
        final Class rawType = Object.class;
        assertIllegalArgumentException(() -> EnumUtils.generateBitVectors(rawType));
    }

    /** A null values array must throw NullPointerException. */
    @Test
    void testGenerateBitVectors_nullArray() {
        assertNullPointerException(() -> EnumUtils.generateBitVectors(Traffic.class, (Traffic[]) null));
    }

    /** A null element inside the values array must throw IllegalArgumentException. */
    @Test
    void testGenerateBitVectors_nullArrayElement() {
        assertIllegalArgumentException(
            () -> EnumUtils.generateBitVectors(Traffic.class, Traffic.RED, null));
    }

    /** A null enumClass (Iterable overload) must throw NullPointerException. */
    @Test
    void testGenerateBitVectors_nullClass() {
        assertNullPointerException(() -> EnumUtils.generateBitVectors(null, EnumSet.of(Traffic.RED)));
    }

    /** A null enumClass (array-varargs overload) must throw NullPointerException. */
    @Test
    void testGenerateBitVectors_nullClassWithArray() {
        assertNullPointerException(() -> EnumUtils.generateBitVectors(null, Traffic.RED));
    }

    /** A null element inside an Iterable must throw NullPointerException. */
    @Test
    void testGenerateBitVectors_nullElement() {
        assertNullPointerException(
            () -> EnumUtils.generateBitVectors(Traffic.class, Arrays.asList(Traffic.RED, null)));
    }

    /** A null Iterable argument must throw NullPointerException. */
    @Test
    void testGenerateBitVectors_nullIterable() {
        assertNullPointerException(() -> EnumUtils.generateBitVectors(null, (Iterable<Traffic>) null));
    }

    // -----------------------------------------------------------------------
    // generateBitVectors (multi-long array/varargs overload)
    // -----------------------------------------------------------------------

    /**
     * Same semantics as the Iterable overload; also verifies idempotent
     * handling of duplicate entries and TooMany's two-long layout.
     */
    @Test
    void testGenerateBitVectorsFromArray() {
        // empty varargs → single-element array with 0
        assertLongArrayEquals(EnumUtils.generateBitVectors(Traffic.class), new long[] { 0L });

        // single Traffic values
        assertLongArrayEquals(EnumUtils.generateBitVectors(Traffic.class, Traffic.RED), 1L);
        assertLongArrayEquals(EnumUtils.generateBitVectors(Traffic.class, Traffic.AMBER), 2L);
        assertLongArrayEquals(EnumUtils.generateBitVectors(Traffic.class, Traffic.GREEN), 4L);

        // pairs and all-three
        assertLongArrayEquals(EnumUtils.generateBitVectors(Traffic.class, Traffic.RED, Traffic.AMBER), 3L);
        assertLongArrayEquals(EnumUtils.generateBitVectors(Traffic.class, Traffic.RED, Traffic.GREEN), 5L);
        assertLongArrayEquals(EnumUtils.generateBitVectors(Traffic.class, Traffic.AMBER, Traffic.GREEN), 6L);
        assertLongArrayEquals(EnumUtils.generateBitVectors(Traffic.class, Traffic.RED, Traffic.AMBER, Traffic.GREEN), 7L);

        // duplicate GREEN should not change the result
        assertLongArrayEquals(
            EnumUtils.generateBitVectors(Traffic.class, Traffic.RED, Traffic.AMBER, Traffic.GREEN, Traffic.GREEN), 7L);

        // 64-value enum: verify no int<->long conversion issue near the 32-/63-bit boundary
        assertLongArrayEquals(EnumUtils.generateBitVectors(Enum64.class, Enum64.A31), 1L << 31);
        assertLongArrayEquals(EnumUtils.generateBitVectors(Enum64.class, Enum64.A32), 1L << 32);
        assertLongArrayEquals(EnumUtils.generateBitVectors(Enum64.class, Enum64.A63), 1L << 63);
        assertLongArrayEquals(EnumUtils.generateBitVectors(Enum64.class, Enum64.A63), Long.MIN_VALUE);

        // TooMany: M2 (ordinal 64) → high long bit 0; L2 (ordinal 63) → low long bit 63
        assertLongArrayEquals(EnumUtils.generateBitVectors(TooMany.class, TooMany.M2), 1L, 0L);
        assertLongArrayEquals(EnumUtils.generateBitVectors(TooMany.class, TooMany.L2, TooMany.M2), 1L, 1L << 63);
    }

    // -----------------------------------------------------------------------
    // getEnum
    // -----------------------------------------------------------------------

    /** Finds existing enum values by exact name; returns null for unknown names or null input. */
    @Test
    void testGetEnum() {
        // known names
        assertEquals(Traffic.RED,   EnumUtils.getEnum(Traffic.class, "RED"));
        assertEquals(Traffic.AMBER, EnumUtils.getEnum(Traffic.class, "AMBER"));
        assertEquals(Traffic.GREEN, EnumUtils.getEnum(Traffic.class, "GREEN"));

        // unknown / null name → null
        assertNull(EnumUtils.getEnum(Traffic.class, "PURPLE"));
        assertNull(EnumUtils.getEnum(Traffic.class, null));
    }

    /**
     * When a name is found the matching enum is returned; when not found or null,
     * the caller-supplied defaultEnum is returned.
     */
    @Test
    void testGetEnum_defaultEnum() {
        // known names return the matched value (default is irrelevant)
        assertEquals(Traffic.RED,   EnumUtils.getEnum(Traffic.class, "RED",   Traffic.AMBER));
        assertEquals(Traffic.AMBER, EnumUtils.getEnum(Traffic.class, "AMBER", Traffic.GREEN));
        assertEquals(Traffic.GREEN, EnumUtils.getEnum(Traffic.class, "GREEN", Traffic.RED));

        // unknown name → returns the provided default
        assertEquals(Traffic.AMBER, EnumUtils.getEnum(Traffic.class, "PURPLE", Traffic.AMBER));
        assertEquals(Traffic.GREEN, EnumUtils.getEnum(Traffic.class, "PURPLE", Traffic.GREEN));
        assertEquals(Traffic.RED,   EnumUtils.getEnum(Traffic.class, "PURPLE", Traffic.RED));

        // null name → returns the provided default
        assertEquals(Traffic.AMBER, EnumUtils.getEnum(Traffic.class, null, Traffic.AMBER));
        assertEquals(Traffic.GREEN, EnumUtils.getEnum(Traffic.class, null, Traffic.GREEN));
        assertEquals(Traffic.RED,   EnumUtils.getEnum(Traffic.class, null, Traffic.RED));

        // null default is propagated as-is when the name is not found
        assertNull(EnumUtils.getEnum(Traffic.class, "PURPLE", null));

        // null enumClass → returns the provided default regardless of name
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

    /** A null enumClass with no default overload returns null. */
    @Test
    void testGetEnum_nullClass() {
        assertNull(EnumUtils.getEnum((Class<Traffic>) null, "PURPLE"));
    }

    // -----------------------------------------------------------------------
    // getEnumIgnoreCase
    // -----------------------------------------------------------------------

    /** Case-insensitive lookup: any capitalisation of a valid name is accepted. */
    @Test
    void testGetEnumIgnoreCase() {
        assertEquals(Traffic.RED,   EnumUtils.getEnumIgnoreCase(Traffic.class, "red"));
        assertEquals(Traffic.AMBER, EnumUtils.getEnumIgnoreCase(Traffic.class, "Amber"));
        assertEquals(Traffic.GREEN, EnumUtils.getEnumIgnoreCase(Traffic.class, "grEEn"));

        // unknown / null name → null
        assertNull(EnumUtils.getEnumIgnoreCase(Traffic.class, "purple"));
        assertNull(EnumUtils.getEnumIgnoreCase(Traffic.class, null));
    }

    /**
     * Default-value variant of case-insensitive lookup.
     * Mirrors the contract of {@link #testGetEnum_defaultEnum()} but with case folding.
     */
    @Test
    void testGetEnumIgnoreCase_defaultEnum() {
        // known names (mixed case) return the matched value
        assertEquals(Traffic.RED,   EnumUtils.getEnumIgnoreCase(Traffic.class, "red",   Traffic.AMBER));
        assertEquals(Traffic.AMBER, EnumUtils.getEnumIgnoreCase(Traffic.class, "Amber", Traffic.GREEN));
        assertEquals(Traffic.GREEN, EnumUtils.getEnumIgnoreCase(Traffic.class, "grEEn", Traffic.RED));

        // unknown name → returns the provided default
        assertEquals(Traffic.AMBER, EnumUtils.getEnumIgnoreCase(Traffic.class, "PURPLE", Traffic.AMBER));
        assertEquals(Traffic.GREEN, EnumUtils.getEnumIgnoreCase(Traffic.class, "purple", Traffic.GREEN));
        assertEquals(Traffic.RED,   EnumUtils.getEnumIgnoreCase(Traffic.class, "pUrPlE", Traffic.RED));

        // null name → returns the provided default
        assertEquals(Traffic.AMBER, EnumUtils.getEnumIgnoreCase(Traffic.class, null, Traffic.AMBER));
        assertEquals(Traffic.GREEN, EnumUtils.getEnumIgnoreCase(Traffic.class, null, Traffic.GREEN));
        assertEquals(Traffic.RED,   EnumUtils.getEnumIgnoreCase(Traffic.class, null, Traffic.RED));

        // null default is propagated as-is when the name is not found
        assertNull(EnumUtils.getEnumIgnoreCase(Traffic.class, "PURPLE", null));
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

    /** A null enumClass with no default overload returns null. */
    @Test
    void testGetEnumIgnoreCase_nullClass() {
        assertNull(EnumUtils.getEnumIgnoreCase((Class<Traffic>) null, "PURPLE"));
    }

    // -----------------------------------------------------------------------
    // getEnumList / getEnumMap
    // -----------------------------------------------------------------------

    /** The list preserves declaration order and contains all three Traffic values. */
    @Test
    void testGetEnumList() {
        final List<Traffic> test = EnumUtils.getEnumList(Traffic.class);
        assertEquals(3, test.size());
        assertEquals(Traffic.RED,   test.get(0));
        assertEquals(Traffic.AMBER, test.get(1));
        assertEquals(Traffic.GREEN, test.get(2));
    }

    /**
     * The default map uses the enum name as key.
     * Verifies both the full-map equality and individual key/value lookups to
     * confirm correct name-to-constant mapping.
     */
    @Test
    void testGetEnumMap() {
        final Map<String, Traffic> test = EnumUtils.getEnumMap(Traffic.class);

        final Map<String, Traffic> expected = new HashMap<>();
        expected.put("RED",   Traffic.RED);
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

    /**
     * Custom key function: Month::getId maps each month constant to its 1-based
     * integer id, so the map is keyed by id rather than by name.
     */
    @Test
    void testGetEnumMap_keyFunction() {
        final Map<Integer, Month> test = EnumUtils.getEnumMap(Month.class, Month::getId);

        final Map<Integer, Month> expected = new HashMap<>();
        expected.put(1,  Month.JAN);
        expected.put(2,  Month.FEB);
        expected.put(3,  Month.MAR);
        expected.put(4,  Month.APR);
        expected.put(5,  Month.MAY);
        expected.put(6,  Month.JUN);
        expected.put(7,  Month.JUL);
        expected.put(8,  Month.AUG);
        expected.put(9,  Month.SEP);
        expected.put(10, Month.OCT);
        expected.put(11, Month.NOV);
        expected.put(12, Month.DEC);
        assertEquals(expected, test, "getEnumMap not created correctly");

        assertEquals(12, test.size());

        // key 0 must be absent (months are 1-based)
        assertFalse(test.containsKey(0));

        // spot-check each month by its numeric id
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

        // key 13 must be absent (only 12 months)
        assertFalse(test.containsKey(13));
    }

    // -----------------------------------------------------------------------
    // getEnumSystemProperty
    // -----------------------------------------------------------------------

    /**
     * Reads the enum value from a system property.
     * Uses the test-class name as the property key to avoid polluting global state,
     * and removes the property in a finally block regardless of test outcome.
     */
    @Test
    void testGetEnumSystemProperty() {
        final String propertyKey = getClass().getName();
        System.setProperty(propertyKey, Traffic.RED.toString());
        try {
            // property exists and maps to a valid enum value
            assertEquals(Traffic.RED, EnumUtils.getEnumSystemProperty(Traffic.class, propertyKey, null));

            // property key does not exist → fallback to default
            assertEquals(Traffic.RED, EnumUtils.getEnumSystemProperty(Traffic.class, "?", Traffic.RED));

            // null enumClass → fallback to default
            assertEquals(Traffic.RED, EnumUtils.getEnumSystemProperty(null, null, Traffic.RED));

            // null enumClass with a real key → fallback to default
            assertEquals(Traffic.RED, EnumUtils.getEnumSystemProperty(null, "?", Traffic.RED));

            // null property key → fallback to default
            assertEquals(Traffic.RED, EnumUtils.getEnumSystemProperty(Traffic.class, null, Traffic.RED));
        } finally {
            System.getProperties().remove(propertyKey);
        }
    }

    // -----------------------------------------------------------------------
    // getFirstEnumIgnoreCase / getFirstEnum
    // -----------------------------------------------------------------------

    /**
     * Looks up the first enum whose label (via the supplied Function) matches the
     * search string case-insensitively.
     * Traffic2 labels are decorated: RED="***Red***", AMBER="**Amber**", GREEN="*green*".
     */
    @Test
    void testGetFirstEnumIgnoreCase_defaultEnum() {
        final Function<Traffic2, String> labelOf = Traffic2::getLabel;

        // label matches (case-insensitive) → returns matched value
        assertEquals(Traffic2.RED,   EnumUtils.getFirstEnumIgnoreCase(Traffic2.class, "***red***", labelOf, Traffic2.AMBER));
        assertEquals(Traffic2.AMBER, EnumUtils.getFirstEnumIgnoreCase(Traffic2.class, "**Amber**", labelOf, Traffic2.GREEN));
        assertEquals(Traffic2.GREEN, EnumUtils.getFirstEnumIgnoreCase(Traffic2.class, "*grEEn*",   labelOf, Traffic2.RED));

        // no label matches → returns the provided default
        assertEquals(Traffic2.AMBER, EnumUtils.getFirstEnumIgnoreCase(Traffic2.class, "PURPLE", labelOf, Traffic2.AMBER));
        assertEquals(Traffic2.GREEN, EnumUtils.getFirstEnumIgnoreCase(Traffic2.class, "purple", labelOf, Traffic2.GREEN));
        assertEquals(Traffic2.RED,   EnumUtils.getFirstEnumIgnoreCase(Traffic2.class, "pUrPlE", labelOf, Traffic2.RED));

        // null search string → returns the provided default
        assertEquals(Traffic2.AMBER, EnumUtils.getFirstEnumIgnoreCase(Traffic2.class, null, labelOf, Traffic2.AMBER));
        assertEquals(Traffic2.GREEN, EnumUtils.getFirstEnumIgnoreCase(Traffic2.class, null, labelOf, Traffic2.GREEN));
        assertEquals(Traffic2.RED,   EnumUtils.getFirstEnumIgnoreCase(Traffic2.class, null, labelOf, Traffic2.RED));

        // null default propagated when not found
        assertNull(EnumUtils.getFirstEnumIgnoreCase(Traffic2.class, "PURPLE", labelOf, null));

        // null enumClass → returns the provided default
        assertNull(EnumUtils.getFirstEnumIgnoreCase(null, "PURPLE", labelOf, null));
    }

    /**
     * Integer-key variant: finds the first enum whose ToIntFunction result equals
     * the given int value.
     * Traffic2 values: RED=1, AMBER=2, GREEN=3.
     */
    @Test
    void testGetFirstEnumToIntFunction() {
        final ToIntFunction<Traffic2> valueOf = Traffic2::getValue;

        // value matches a known enum constant → returns matched value
        assertEquals(Traffic2.RED,   EnumUtils.getFirstEnum(Traffic2.class, 1, valueOf, Traffic2.AMBER));
        assertEquals(Traffic2.AMBER, EnumUtils.getFirstEnum(Traffic2.class, 2, valueOf, Traffic2.GREEN));
        assertEquals(Traffic2.GREEN, EnumUtils.getFirstEnum(Traffic2.class, 3, valueOf, Traffic2.RED));

        // value does not match any constant → returns the provided default
        assertEquals(Traffic2.AMBER, EnumUtils.getFirstEnum(Traffic2.class, 4, valueOf, Traffic2.AMBER));
        assertEquals(Traffic2.GREEN, EnumUtils.getFirstEnum(Traffic2.class, 5, valueOf, Traffic2.GREEN));
        assertEquals(Traffic2.RED,   EnumUtils.getFirstEnum(Traffic2.class, 6, valueOf, Traffic2.RED));
        assertEquals(Traffic2.AMBER, EnumUtils.getFirstEnum(Traffic2.class, 0, valueOf, Traffic2.AMBER));
        assertEquals(Traffic2.GREEN, EnumUtils.getFirstEnum(Traffic2.class, -1, valueOf, Traffic2.GREEN));
        assertEquals(Traffic2.RED,   EnumUtils.getFirstEnum(Traffic2.class, 0, valueOf, Traffic2.RED));

        // no match, null default
        assertNull(EnumUtils.getFirstEnum(Traffic2.class, 7, valueOf, null));

        // null or non-enum enumClass → returns the provided default
        assertEquals(Traffic2.AMBER, EnumUtils.getFirstEnum(null, 1, valueOf, Traffic2.AMBER));
        assertEquals(Traffic2.AMBER, EnumUtils.getFirstEnum((Class) String.class, 1, valueOf, Traffic2.AMBER));
    }

    // -----------------------------------------------------------------------
    // isValidEnum / isValidEnumIgnoreCase
    // -----------------------------------------------------------------------

    /** Valid names return true; unknown names or null return false. */
    @Test
    void testIsValidEnum() {
        assertTrue(EnumUtils.isValidEnum(Traffic.class, "RED"));
        assertTrue(EnumUtils.isValidEnum(Traffic.class, "AMBER"));
        assertTrue(EnumUtils.isValidEnum(Traffic.class, "GREEN"));
        assertFalse(EnumUtils.isValidEnum(Traffic.class, "PURPLE"));
        assertFalse(EnumUtils.isValidEnum(Traffic.class, null));
    }

    /** A null enumClass always returns false. */
    @Test
    void testIsValidEnum_nullClass() {
        assertFalse(EnumUtils.isValidEnum(null, "PURPLE"));
    }

    /** Case-insensitive validation: any capitalisation of a valid name returns true. */
    @Test
    void testIsValidEnumIgnoreCase() {
        assertTrue(EnumUtils.isValidEnumIgnoreCase(Traffic.class, "red"));
        assertTrue(EnumUtils.isValidEnumIgnoreCase(Traffic.class, "Amber"));
        assertTrue(EnumUtils.isValidEnumIgnoreCase(Traffic.class, "grEEn"));
        assertFalse(EnumUtils.isValidEnumIgnoreCase(Traffic.class, "purple"));
        assertFalse(EnumUtils.isValidEnumIgnoreCase(Traffic.class, null));
    }

    /** A null enumClass always returns false (case-insensitive variant). */
    @Test
    void testIsValidEnumIgnoreCase_nullClass() {
        assertFalse(EnumUtils.isValidEnumIgnoreCase(null, "PURPLE"));
    }

    // -----------------------------------------------------------------------
    // processBitVector (single long → EnumSet)
    // -----------------------------------------------------------------------

    /**
     * Converts a single-long bit mask back to the corresponding EnumSet.
     * Bit positions mirror those produced by generateBitVector:
     * RED=bit0, AMBER=bit1, GREEN=bit2.
     */
    @Test
    void testProcessBitVector() {
        // empty set
        assertEquals(EnumSet.noneOf(Traffic.class), EnumUtils.processBitVector(Traffic.class, 0L));

        // single values
        assertEquals(EnumSet.of(Traffic.RED),   EnumUtils.processBitVector(Traffic.class, 1L));
        assertEquals(EnumSet.of(Traffic.AMBER), EnumUtils.processBitVector(Traffic.class, 2L));

        // pairs
        assertEquals(EnumSet.of(Traffic.RED, Traffic.AMBER), EnumUtils.processBitVector(Traffic.class, 3L));

        assertEquals(EnumSet.of(Traffic.GREEN),              EnumUtils.processBitVector(Traffic.class, 4L));
        assertEquals(EnumSet.of(Traffic.RED, Traffic.GREEN), EnumUtils.processBitVector(Traffic.class, 5L));
        assertEquals(EnumSet.of(Traffic.AMBER, Traffic.GREEN), EnumUtils.processBitVector(Traffic.class, 6L));

        // all three
        assertEquals(EnumSet.of(Traffic.RED, Traffic.AMBER, Traffic.GREEN),
            EnumUtils.processBitVector(Traffic.class, 7L));

        // 64-value enum: verify no int<->long conversion issue near the 32-/63-bit boundary
        assertEquals(EnumSet.of(Enum64.A31), EnumUtils.processBitVector(Enum64.class, 1L << 31));
        assertEquals(EnumSet.of(Enum64.A32), EnumUtils.processBitVector(Enum64.class, 1L << 32));
        assertEquals(EnumSet.of(Enum64.A63), EnumUtils.processBitVector(Enum64.class, 1L << 63));
        // Long.MIN_VALUE is the same bit pattern as 1L<<63
        assertEquals(EnumSet.of(Enum64.A63), EnumUtils.processBitVector(Enum64.class, Long.MIN_VALUE));
    }

    /** Enums with more than 64 values are not compatible with the single-long overload. */
    @Test
    void testProcessBitVector_longClass() {
        assertIllegalArgumentException(() -> EnumUtils.processBitVector(TooMany.class, 0L));
    }

    /** A null enumClass must throw NullPointerException. */
    @Test
    void testProcessBitVector_nullClass() {
        final Class<Traffic> empty = null;
        assertNullPointerException(() -> EnumUtils.processBitVector(empty, 0L));
    }

    // -----------------------------------------------------------------------
    // processBitVectors (long[] → EnumSet)
    // -----------------------------------------------------------------------

    /**
     * Multi-long overload for Traffic (fits in one long).
     * Tests with a single long, two longs (high word = 0), and tolerance for
     * irrelevant high-order digits in the high word.
     */
    @Test
    void testProcessBitVectors() {
        // --- single-long input (same as processBitVector for small enums) ---
        assertEquals(EnumSet.noneOf(Traffic.class), EnumUtils.processBitVectors(Traffic.class, 0L));
        assertEquals(EnumSet.of(Traffic.RED),   EnumUtils.processBitVectors(Traffic.class, 1L));
        assertEquals(EnumSet.of(Traffic.AMBER), EnumUtils.processBitVectors(Traffic.class, 2L));
        assertEquals(EnumSet.of(Traffic.RED, Traffic.AMBER), EnumUtils.processBitVectors(Traffic.class, 3L));
        assertEquals(EnumSet.of(Traffic.GREEN),              EnumUtils.processBitVectors(Traffic.class, 4L));
        assertEquals(EnumSet.of(Traffic.RED, Traffic.GREEN), EnumUtils.processBitVectors(Traffic.class, 5L));
        assertEquals(EnumSet.of(Traffic.AMBER, Traffic.GREEN), EnumUtils.processBitVectors(Traffic.class, 6L));
        assertEquals(EnumSet.of(Traffic.RED, Traffic.AMBER, Traffic.GREEN),
            EnumUtils.processBitVectors(Traffic.class, 7L));

        // --- two-long input with high word = 0 (should behave identically) ---
        assertEquals(EnumSet.noneOf(Traffic.class), EnumUtils.processBitVectors(Traffic.class, 0L, 0L));
        assertEquals(EnumSet.of(Traffic.RED),   EnumUtils.processBitVectors(Traffic.class, 0L, 1L));
        assertEquals(EnumSet.of(Traffic.AMBER), EnumUtils.processBitVectors(Traffic.class, 0L, 2L));
        assertEquals(EnumSet.of(Traffic.RED, Traffic.AMBER), EnumUtils.processBitVectors(Traffic.class, 0L, 3L));
        assertEquals(EnumSet.of(Traffic.GREEN),              EnumUtils.processBitVectors(Traffic.class, 0L, 4L));
        assertEquals(EnumSet.of(Traffic.RED, Traffic.GREEN), EnumUtils.processBitVectors(Traffic.class, 0L, 5L));
        assertEquals(EnumSet.of(Traffic.AMBER, Traffic.GREEN), EnumUtils.processBitVectors(Traffic.class, 0L, 6L));
        assertEquals(EnumSet.of(Traffic.RED, Traffic.AMBER, Traffic.GREEN),
            EnumUtils.processBitVectors(Traffic.class, 0L, 7L));

        // --- two-long input with irrelevant bits in the high word (must be ignored) ---
        assertEquals(EnumSet.noneOf(Traffic.class), EnumUtils.processBitVectors(Traffic.class, 666L, 0L));
        assertEquals(EnumSet.of(Traffic.RED),   EnumUtils.processBitVectors(Traffic.class, 666L, 1L));
        assertEquals(EnumSet.of(Traffic.AMBER), EnumUtils.processBitVectors(Traffic.class, 666L, 2L));
        assertEquals(EnumSet.of(Traffic.RED, Traffic.AMBER), EnumUtils.processBitVectors(Traffic.class, 666L, 3L));
        assertEquals(EnumSet.of(Traffic.GREEN),              EnumUtils.processBitVectors(Traffic.class, 666L, 4L));
        assertEquals(EnumSet.of(Traffic.RED, Traffic.GREEN), EnumUtils.processBitVectors(Traffic.class, 666L, 5L));
        assertEquals(EnumSet.of(Traffic.AMBER, Traffic.GREEN), EnumUtils.processBitVectors(Traffic.class, 666L, 6L));
        assertEquals(EnumSet.of(Traffic.RED, Traffic.AMBER, Traffic.GREEN),
            EnumUtils.processBitVectors(Traffic.class, 666L, 7L));

        // 64-value enum: verify no int<->long conversion issue near the 32-/63-bit boundary
        assertEquals(EnumSet.of(Enum64.A31), EnumUtils.processBitVectors(Enum64.class, 1L << 31));
        assertEquals(EnumSet.of(Enum64.A32), EnumUtils.processBitVectors(Enum64.class, 1L << 32));
        assertEquals(EnumSet.of(Enum64.A63), EnumUtils.processBitVectors(Enum64.class, 1L << 63));
        assertEquals(EnumSet.of(Enum64.A63), EnumUtils.processBitVectors(Enum64.class, Long.MIN_VALUE));
    }

    /**
     * processBitVectors supports TooMany (65 values) which requires two longs.
     * The low long covers ordinals 0–63; the high long covers ordinal 64 (M2).
     */
    @Test
    void testProcessBitVectors_longClass() {
        // --- single-long input: only ordinals 0-63 are addressable ---
        assertEquals(EnumSet.noneOf(TooMany.class), EnumUtils.processBitVectors(TooMany.class, 0L));
        assertEquals(EnumSet.of(TooMany.A), EnumUtils.processBitVectors(TooMany.class, 1L));
        assertEquals(EnumSet.of(TooMany.B), EnumUtils.processBitVectors(TooMany.class, 2L));
        assertEquals(EnumSet.of(TooMany.A, TooMany.B), EnumUtils.processBitVectors(TooMany.class, 3L));
        assertEquals(EnumSet.of(TooMany.C), EnumUtils.processBitVectors(TooMany.class, 4L));
        assertEquals(EnumSet.of(TooMany.A, TooMany.C), EnumUtils.processBitVectors(TooMany.class, 5L));
        assertEquals(EnumSet.of(TooMany.B, TooMany.C), EnumUtils.processBitVectors(TooMany.class, 6L));
        assertEquals(EnumSet.of(TooMany.A, TooMany.B, TooMany.C), EnumUtils.processBitVectors(TooMany.class, 7L));

        // --- two-long input, high word = 0 (equivalent to single-long for first 64 ordinals) ---
        assertEquals(EnumSet.noneOf(TooMany.class), EnumUtils.processBitVectors(TooMany.class, 0L, 0L));
        assertEquals(EnumSet.of(TooMany.A), EnumUtils.processBitVectors(TooMany.class, 0L, 1L));
        assertEquals(EnumSet.of(TooMany.B), EnumUtils.processBitVectors(TooMany.class, 0L, 2L));
        assertEquals(EnumSet.of(TooMany.A, TooMany.B), EnumUtils.processBitVectors(TooMany.class, 0L, 3L));
        assertEquals(EnumSet.of(TooMany.C), EnumUtils.processBitVectors(TooMany.class, 0L, 4L));
        assertEquals(EnumSet.of(TooMany.A, TooMany.C), EnumUtils.processBitVectors(TooMany.class, 0L, 5L));
        assertEquals(EnumSet.of(TooMany.B, TooMany.C), EnumUtils.processBitVectors(TooMany.class, 0L, 6L));
        assertEquals(EnumSet.of(TooMany.A, TooMany.B, TooMany.C), EnumUtils.processBitVectors(TooMany.class, 0L, 7L));
        assertEquals(EnumSet.of(TooMany.A, TooMany.B, TooMany.C), EnumUtils.processBitVectors(TooMany.class, 0L, 7L));

        // --- two-long input, high word = 1 (M2 at ordinal 64 is in bit 0 of the high long) ---
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

        // --- tolerance for irrelevant high-order digits in the high word ---
        // high word=9 (binary 1001): only the lowest bit (M2) is a valid ordinal for TooMany,
        // so bits above ordinal 64 are silently ignored.
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

    /** A null enumClass must throw NullPointerException. */
    @Test
    void testProcessBitVectors_nullClass() {
        final Class<Traffic> empty = null;
        assertNullPointerException(() -> EnumUtils.processBitVectors(empty, 0L));
    }

    // -----------------------------------------------------------------------
    // stream
    // -----------------------------------------------------------------------

    /**
     * Stream over an enum class returns all constants in declaration order;
     * non-enum and null classes return an empty stream.
     */
    @Test
    void testStream() {
        // TimeUnit has exactly 7 constants
        assertEquals(7, EnumUtils.stream(TimeUnit.class).count());
        assertArrayEquals(TimeUnit.values(), EnumUtils.stream(TimeUnit.class).toArray(TimeUnit[]::new));

        // non-enum class and null produce empty streams
        assertEquals(0, EnumUtils.stream(Object.class).count());
        assertEquals(0, EnumUtils.stream(null).count());
    }

}

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

enum TooMany {
    A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z, A1, B1, C1, D1, E1, F1, G1, H1, I1,
    J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1, U1, V1, W1, X1, Y1, Z1, A2, B2, C2, D2, E2, F2, G2, H2, I2, J2, K2, L2,
    M2
}

enum Traffic {
    RED, AMBER, GREEN
}

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
