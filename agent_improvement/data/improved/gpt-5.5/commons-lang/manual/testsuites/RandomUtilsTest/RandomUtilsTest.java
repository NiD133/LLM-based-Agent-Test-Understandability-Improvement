/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.lang3;

import static org.apache.commons.lang3.LangAssertions.assertIllegalArgumentException;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests for {@link RandomUtils}.
 */
class RandomUtilsTest extends AbstractLangTest {

    /** For comparing doubles and floats. */
    private static final double DELTA = 1e-5;

    private static final int BYTE_COUNT = 20;
    private static final double DOUBLE_RANGE_START = 33d;
    private static final double DOUBLE_RANGE_END = 42d;
    private static final float FLOAT_RANGE_START = 33f;
    private static final float FLOAT_RANGE_END = 42f;
    private static final int INTEGER_RANGE_START = 33;
    private static final int INTEGER_RANGE_END = 42;
    private static final long LONG_RANGE_START = 33L;
    private static final long LONG_RANGE_END = 42L;
    private static final double MINIMAL_DOUBLE_RANGE_VALUE = 42.1;
    private static final float MINIMAL_FLOAT_RANGE_VALUE = 42.1f;
    private static final int MINIMAL_INTEGER_RANGE_VALUE = 42;
    private static final long MINIMAL_LONG_RANGE_VALUE = 42L;
    private static final long LARGE_LONG_RANGE_START = 12900000000001L;
    private static final long LARGE_LONG_RANGE_END = 12900000000016L;
    private static final int LARGE_LONG_RANGE_ATTEMPTS = (int) (LARGE_LONG_RANGE_END - LARGE_LONG_RANGE_START) * 1000;

    static Stream<RandomUtils> randomProvider() {
        return Stream.of(RandomUtils.secure(), RandomUtils.secureStrong(), RandomUtils.insecure());
    }

    private static void assertDoubleInRange(final double value, final double startInclusive, final double endExclusive) {
        assertTrue(value >= startInclusive);
        assertTrue(value < endExclusive);
    }

    private static void assertFloatInRange(final float value, final float startInclusive, final float endExclusive) {
        assertTrue(value >= startInclusive);
        assertTrue(value < endExclusive);
    }

    private static void assertIntInRange(final int value, final int startInclusive, final int endExclusive) {
        assertTrue(value >= startInclusive);
        assertTrue(value < endExclusive);
    }

    private static void assertLongInRange(final long value, final long startInclusive, final long endExclusive) {
        assertTrue(value >= startInclusive);
        assertTrue(value < endExclusive);
    }

    private static void assertBooleanValue(final boolean value) {
        assertTrue(value || !value);
    }

    private static void assertLargeLongRangeNeverReturnsUpperBound(final long value) {
        assertNotEquals(LARGE_LONG_RANGE_END, value);
    }

    /**
     * Tests next boolean.
     */
    @Test
    void testBoolean() {
        assertBooleanValue(RandomUtils.nextBoolean());
    }

    @ParameterizedTest
    @MethodSource("randomProvider")
    void testBoolean(final RandomUtils ru) {
        assertBooleanValue(ru.randomBoolean());
    }

    @Test
    void testConstructor() {
        assertNotNull(new RandomUtils());
    }

    /**
     * Tests extreme range.
     */
    @Test
    void testExtremeRangeDouble() {
        final double result = RandomUtils.nextDouble(0, Double.MAX_VALUE);
        assertTrue(result >= 0 && result <= Double.MAX_VALUE); // TODO: should be <max?
    }

    @ParameterizedTest
    @MethodSource("randomProvider")
    void testExtremeRangeDouble(final RandomUtils ru) {
        final double result = ru.randomDouble(0, Double.MAX_VALUE);
        assertTrue(result >= 0 && result <= Double.MAX_VALUE); // TODO: should be <max?
    }

    /**
     * Tests extreme range.
     */
    @Test
    void testExtremeRangeFloat() {
        final float result = RandomUtils.nextFloat(0, Float.MAX_VALUE);
        assertTrue(result >= 0f && result <= Float.MAX_VALUE); // TODO: should be <max?
    }

    /**
     * Tests extreme range.
     */
    @ParameterizedTest
    @MethodSource("randomProvider")
    void testExtremeRangeFloat(final RandomUtils ru) {
        final float result = ru.randomFloat(0, Float.MAX_VALUE);
        assertTrue(result >= 0f && result <= Float.MAX_VALUE); // TODO: should be <max?
    }

    /**
     * Tests extreme range.
     */
    @Test
    void testExtremeRangeInt() {
        assertIntInRange(RandomUtils.nextInt(0, Integer.MAX_VALUE), 0, Integer.MAX_VALUE);
    }

    /**
     * Tests extreme range.
     */
    @ParameterizedTest
    @MethodSource("randomProvider")
    void testExtremeRangeInt(final RandomUtils ru) {
        assertIntInRange(ru.randomInt(0, Integer.MAX_VALUE), 0, Integer.MAX_VALUE);
    }

    /**
     * Tests extreme range.
     */
    @Test
    void testExtremeRangeLong() {
        assertLongInRange(RandomUtils.nextLong(0, Long.MAX_VALUE), 0, Long.MAX_VALUE);
    }

    /**
     * Tests extreme range.
     */
    @ParameterizedTest
    @MethodSource("randomProvider")
    void testExtremeRangeLong(final RandomUtils ru) {
        assertLongInRange(ru.randomLong(0, Long.MAX_VALUE), 0, Long.MAX_VALUE);
    }

    /**
     * Test a large value for long. A previous implementation using
     * {@link RandomUtils#nextDouble(double, double)} could generate a value equal
     * to the upper limit.
     *
     * <pre>
     * return (long) nextDouble(startInclusive, endExclusive);
     * </pre>
     *
     * <p>See LANG-1592.</p>
     */
    @Test
    void testLargeValueRangeLong() {
        for (int i = 0; i < LARGE_LONG_RANGE_ATTEMPTS; i++) {
            assertLargeLongRangeNeverReturnsUpperBound(RandomUtils.nextLong(LARGE_LONG_RANGE_START, LARGE_LONG_RANGE_END));
        }
    }

    /**
     * Test a large value for long. A previous implementation using
     * {@link RandomUtils#nextDouble(double, double)} could generate a value equal
     * to the upper limit.
     *
     * <pre>
     * return (long) nextDouble(startInclusive, endExclusive);
     * </pre>
     *
     * <p>See LANG-1592.</p>
     */
    @ParameterizedTest
    @MethodSource("randomProvider")
    void testLargeValueRangeLong(final RandomUtils ru) {
        for (int i = 0; i < LARGE_LONG_RANGE_ATTEMPTS; i++) {
            assertLargeLongRangeNeverReturnsUpperBound(ru.randomLong(LARGE_LONG_RANGE_START, LARGE_LONG_RANGE_END));
        }
    }

    /**
     * Tests random byte array.
     */
    @Test
    void testNextBytes() {
        assertEquals(BYTE_COUNT, RandomUtils.nextBytes(BYTE_COUNT).length);
    }

    /**
     * Tests random byte array.
     */
    @ParameterizedTest
    @MethodSource("randomProvider")
    void testNextBytes(final RandomUtils ru) {
        assertEquals(BYTE_COUNT, ru.randomBytes(BYTE_COUNT).length);
    }

    @Test
    void testNextBytesNegative() {
        assertIllegalArgumentException(() -> RandomUtils.nextBytes(-1));
    }

    @ParameterizedTest
    @MethodSource("randomProvider")
    void testNextBytesNegative(final RandomUtils ru) {
        assertIllegalArgumentException(() -> ru.randomBytes(-1));
    }

    /**
     * Tests next double range.
     */
    @Test
    void testNextDouble() {
        assertDoubleInRange(RandomUtils.nextDouble(DOUBLE_RANGE_START, DOUBLE_RANGE_END), DOUBLE_RANGE_START, DOUBLE_RANGE_END);
    }

    /**
     * Tests next double range.
     */
    @ParameterizedTest
    @MethodSource("randomProvider")
    void testNextDouble(final RandomUtils ru) {
        assertDoubleInRange(ru.randomDouble(DOUBLE_RANGE_START, DOUBLE_RANGE_END), DOUBLE_RANGE_START, DOUBLE_RANGE_END);
    }

    @Test
    void testNextDoubleLowerGreaterUpper() {
        assertIllegalArgumentException(() -> RandomUtils.nextDouble(2, 1));
    }

    @ParameterizedTest
    @MethodSource("randomProvider")
    void testNextDoubleLowerGreaterUpper(final RandomUtils ru) {
        assertIllegalArgumentException(() -> ru.randomDouble(2, 1));
    }

    /**
     * Test next double range with minimal range.
     */
    @Test
    void testNextDoubleMinimalRange() {
        assertEquals(MINIMAL_DOUBLE_RANGE_VALUE, RandomUtils.nextDouble(42.1, 42.1), DELTA);
    }

    /**
     * Test next double range with minimal range.
     */
    @ParameterizedTest
    @MethodSource("randomProvider")
    void testNextDoubleMinimalRange(final RandomUtils ru) {
        assertEquals(MINIMAL_DOUBLE_RANGE_VALUE, ru.randomDouble(42.1, 42.1), DELTA);
    }

    @Test
    void testNextDoubleNegative() {
        assertIllegalArgumentException(() -> RandomUtils.nextDouble(-1, 1));
    }

    @ParameterizedTest
    @MethodSource("randomProvider")
    void testNextDoubleNegative(final RandomUtils ru) {
        assertIllegalArgumentException(() -> ru.randomDouble(-1, 1));
    }

    /**
     * Tests next double range, random result.
     */
    @Test
    void testNextDoubleRandomResult() {
        assertDoubleInRange(RandomUtils.nextDouble(), 0d, Double.MAX_VALUE);
    }

    /**
     * Tests next double range, random result.
     */
    @ParameterizedTest
    @MethodSource("randomProvider")
    void testNextDoubleRandomResult(final RandomUtils ru) {
        assertDoubleInRange(ru.randomDouble(), 0d, Double.MAX_VALUE);
    }

    /**
     * Tests next float range.
     */
    @Test
    void testNextFloat() {
        assertFloatInRange(RandomUtils.nextFloat(FLOAT_RANGE_START, FLOAT_RANGE_END), FLOAT_RANGE_START, FLOAT_RANGE_END);
    }

    /**
     * Tests next float range.
     */
    @ParameterizedTest
    @MethodSource("randomProvider")
    void testNextFloat(final RandomUtils ru) {
        assertFloatInRange(ru.randomFloat(FLOAT_RANGE_START, FLOAT_RANGE_END), FLOAT_RANGE_START, FLOAT_RANGE_END);
    }

    @Test
    void testNextFloatLowerGreaterUpper() {
        assertIllegalArgumentException(() -> RandomUtils.nextFloat(2, 1));
    }

    @ParameterizedTest
    @MethodSource("randomProvider")
    void testNextFloatLowerGreaterUpper(final RandomUtils ru) {
        assertIllegalArgumentException(() -> ru.randomFloat(2, 1));
    }

    /**
     * Test next float range with minimal range.
     */
    @Test
    void testNextFloatMinimalRange() {
        assertEquals(MINIMAL_FLOAT_RANGE_VALUE, RandomUtils.nextFloat(42.1f, 42.1f), DELTA);
    }

    /**
     * Test next float range with minimal range.
     */
    @ParameterizedTest
    @MethodSource("randomProvider")
    void testNextFloatMinimalRange(final RandomUtils ru) {
        assertEquals(MINIMAL_FLOAT_RANGE_VALUE, ru.randomFloat(42.1f, 42.1f), DELTA);
    }

    @Test
    void testNextFloatNegative() {
        assertIllegalArgumentException(() -> RandomUtils.nextFloat(-1, 1));
    }

    @ParameterizedTest
    @MethodSource("randomProvider")
    void testNextFloatNegative(final RandomUtils ru) {
        assertIllegalArgumentException(() -> ru.randomFloat(-1, 1));
    }

    /**
     * Tests next float range, random result.
     */
    @Test
    void testNextFloatRandomResult() {
        assertFloatInRange(RandomUtils.nextFloat(), 0f, Float.MAX_VALUE);
    }

    /**
     * Tests next float range, random result.
     */
    @ParameterizedTest
    @MethodSource("randomProvider")
    void testNextFloatRandomResult(final RandomUtils ru) {
        assertFloatInRange(ru.randomFloat(), 0f, Float.MAX_VALUE);
    }

    /**
     * Tests next int range.
     */
    @Test
    void testNextInt() {
        assertIntInRange(RandomUtils.nextInt(INTEGER_RANGE_START, INTEGER_RANGE_END), INTEGER_RANGE_START, INTEGER_RANGE_END);
    }

    /**
     * Tests next int range.
     */
    @ParameterizedTest
    @MethodSource("randomProvider")
    void testNextInt(final RandomUtils ru) {
        assertIntInRange(ru.randomInt(INTEGER_RANGE_START, INTEGER_RANGE_END), INTEGER_RANGE_START, INTEGER_RANGE_END);
    }

    @Test
    void testNextIntLowerGreaterUpper() {
        assertIllegalArgumentException(() -> RandomUtils.nextInt(2, 1));
    }

    @ParameterizedTest
    @MethodSource("randomProvider")
    void testNextIntLowerGreaterUpper(final RandomUtils ru) {
        assertIllegalArgumentException(() -> ru.randomInt(2, 1));
    }

    /**
     * Test next int range with minimal range.
     */
    @Test
    void testNextIntMinimalRange() {
        assertEquals(MINIMAL_INTEGER_RANGE_VALUE, RandomUtils.nextInt(42, 42));
    }

    /**
     * Test next int range with minimal range.
     */
    @ParameterizedTest
    @MethodSource("randomProvider")
    void testNextIntMinimalRange(final RandomUtils ru) {
        assertEquals(MINIMAL_INTEGER_RANGE_VALUE, ru.randomInt(42, 42));
    }

    @Test
    void testNextIntNegative() {
        assertIllegalArgumentException(() -> RandomUtils.nextInt(-1, 1));
    }

    @ParameterizedTest
    @MethodSource("randomProvider")
    void testNextIntNegative(final RandomUtils ru) {
        assertIllegalArgumentException(() -> ru.randomInt(-1, 1));
    }

    /**
     * Tests next int range, random result.
     */
    @Test
    void testNextIntRandomResult() {
        final int randomResult = RandomUtils.nextInt();
        assertTrue(randomResult > 0);
        assertTrue(randomResult < Integer.MAX_VALUE);
    }

    /**
     * Tests next int range, random result.
     */
    @ParameterizedTest
    @MethodSource("randomProvider")
    void testNextIntRandomResult(final RandomUtils ru) {
        final int randomResult = ru.randomInt();
        assertTrue(randomResult > 0);
        assertTrue(randomResult < Integer.MAX_VALUE);
    }

    /**
     * Tests next long range.
     */
    @Test
    void testNextLong() {
        assertLongInRange(RandomUtils.nextLong(LONG_RANGE_START, LONG_RANGE_END), LONG_RANGE_START, LONG_RANGE_END);
    }

    /**
     * Tests next long range.
     */
    @ParameterizedTest
    @MethodSource("randomProvider")
    void testNextLong(final RandomUtils ru) {
        assertLongInRange(ru.randomLong(LONG_RANGE_START, LONG_RANGE_END), LONG_RANGE_START, LONG_RANGE_END);
    }

    @Test
    void testNextLongLowerGreaterUpper() {
        assertIllegalArgumentException(() -> RandomUtils.nextLong(2, 1));
    }

    @ParameterizedTest
    @MethodSource("randomProvider")
    void testNextLongLowerGreaterUpper(final RandomUtils ru) {
        assertIllegalArgumentException(() -> ru.randomLong(2, 1));
    }

    /**
     * Test next long range with minimal range.
     */
    @Test
    void testNextLongMinimalRange() {
        assertEquals(MINIMAL_LONG_RANGE_VALUE, RandomUtils.nextLong(42L, 42L));
    }

    /**
     * Test next long range with minimal range.
     */
    @ParameterizedTest
    @MethodSource("randomProvider")
    void testNextLongMinimalRange(final RandomUtils ru) {
        assertEquals(MINIMAL_LONG_RANGE_VALUE, ru.randomLong(42L, 42L));
    }

    @Test
    void testNextLongNegative() {
        assertIllegalArgumentException(() -> RandomUtils.nextLong(-1, 1));
    }

    @ParameterizedTest
    @MethodSource("randomProvider")
    void testNextLongNegative(final RandomUtils ru) {
        assertIllegalArgumentException(() -> ru.randomLong(-1, 1));
    }

    /**
     * Tests next long range, random result.
     */
    @Test
    void testNextLongRandomResult() {
        assertLongInRange(RandomUtils.nextLong(), 0L, Long.MAX_VALUE);
    }

    /**
     * Tests next long range, random result.
     */
    @ParameterizedTest
    @MethodSource("randomProvider")
    void testNextLongRandomResult(final RandomUtils ru) {
        assertLongInRange(ru.randomLong(), 0L, Long.MAX_VALUE);
    }

    /**
     * Tests a zero byte array length.
     */
    @Test
    void testZeroLengthNextBytes() {
        assertArrayEquals(new byte[0], RandomUtils.nextBytes(0));
    }

    /**
     * Tests a zero byte array length.
     */
    @ParameterizedTest
    @MethodSource("randomProvider")
    void testZeroLengthNextBytes(final RandomUtils ru) {
        assertArrayEquals(new byte[0], ru.randomBytes(0));
    }
}
