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
 *
 * <p>Each feature is tested twice: once via the deprecated static convenience methods
 * (e.g. {@link RandomUtils#nextInt()}) and once via the three instance-based providers
 * ({@link RandomUtils#secure()}, {@link RandomUtils#secureStrong()},
 * {@link RandomUtils#insecure()}) exercised through the {@link #randomProvider()} source.</p>
 */
class RandomUtilsTest extends AbstractLangTest {

    /**
     * Tolerance used when comparing floating-point values with {@link org.junit.jupiter.api.Assertions#assertEquals}.
     * Required because floating-point arithmetic does not guarantee exact equality.
     */
    private static final double DELTA = 1e-5;

    /**
     * Provides the three standard {@link RandomUtils} instances (secure, secureStrong, insecure)
     * as parameterized test arguments so each test scenario is verified for every provider.
     *
     * @return stream of {@link RandomUtils} instances to test.
     */
    static Stream<RandomUtils> randomProvider() {
        return Stream.of(RandomUtils.secure(), RandomUtils.secureStrong(), RandomUtils.insecure());
    }

    // -------------------------------------------------------------------------
    // Constructor
    // -------------------------------------------------------------------------

    /**
     * Tests that the public no-arg constructor (required for JavaBean tooling) can be
     * instantiated without error, even though direct instantiation is discouraged.
     */
    @Test
    void testConstructor() {
        assertNotNull(new RandomUtils());
    }

    // -------------------------------------------------------------------------
    // Boolean
    // -------------------------------------------------------------------------

    /**
     * Tests that {@link RandomUtils#nextBoolean()} returns without throwing an exception.
     * Any boolean value (true or false) is a valid result, so the assertion is a tautology
     * that merely confirms the method executed successfully.
     */
    @Test
    void testBoolean() {
        final boolean result = RandomUtils.nextBoolean();
        assertTrue(result || !result);
    }

    /**
     * Tests that {@link RandomUtils#randomBoolean()} returns without throwing an exception
     * for every provider in {@link #randomProvider()}.
     * Any boolean value (true or false) is a valid result, so the assertion is a tautology
     * that merely confirms the method executed successfully.
     *
     * @param randomUtils the {@link RandomUtils} instance under test.
     */
    @ParameterizedTest
    @MethodSource("randomProvider")
    void testBoolean(final RandomUtils randomUtils) {
        final boolean result = randomUtils.randomBoolean();
        assertTrue(result || !result);
    }

    // -------------------------------------------------------------------------
    // Bytes
    // -------------------------------------------------------------------------

    /**
     * Tests that {@link RandomUtils#nextBytes(int)} returns an array whose length equals
     * the requested count.
     */
    @Test
    void testNextBytes() {
        final byte[] result = RandomUtils.nextBytes(20);
        assertEquals(20, result.length);
    }

    /**
     * Tests that {@link RandomUtils#randomBytes(int)} returns an array whose length equals
     * the requested count for every provider in {@link #randomProvider()}.
     *
     * @param randomUtils the {@link RandomUtils} instance under test.
     */
    @ParameterizedTest
    @MethodSource("randomProvider")
    void testNextBytes(final RandomUtils randomUtils) {
        final byte[] result = randomUtils.randomBytes(20);
        assertEquals(20, result.length);
    }

    /**
     * Tests that {@link RandomUtils#nextBytes(int)} throws {@link IllegalArgumentException}
     * when a negative count is requested.
     */
    @Test
    void testNextBytesNegative() {
        assertIllegalArgumentException(() -> RandomUtils.nextBytes(-1));
    }

    /**
     * Tests that {@link RandomUtils#randomBytes(int)} throws {@link IllegalArgumentException}
     * when a negative count is requested, for every provider in {@link #randomProvider()}.
     *
     * @param randomUtils the {@link RandomUtils} instance under test.
     */
    @ParameterizedTest
    @MethodSource("randomProvider")
    void testNextBytesNegative(final RandomUtils randomUtils) {
        assertIllegalArgumentException(() -> randomUtils.randomBytes(-1));
    }

    /**
     * Tests that {@link RandomUtils#nextBytes(int)} returns an empty array when a count of
     * zero is requested.
     */
    @Test
    void testZeroLengthNextBytes() {
        assertArrayEquals(new byte[0], RandomUtils.nextBytes(0));
    }

    /**
     * Tests that {@link RandomUtils#randomBytes(int)} returns an empty array when a count of
     * zero is requested, for every provider in {@link #randomProvider()}.
     *
     * @param randomUtils the {@link RandomUtils} instance under test.
     */
    @ParameterizedTest
    @MethodSource("randomProvider")
    void testZeroLengthNextBytes(final RandomUtils randomUtils) {
        assertArrayEquals(new byte[0], randomUtils.randomBytes(0));
    }

    // -------------------------------------------------------------------------
    // Double
    // -------------------------------------------------------------------------

    /**
     * Tests that {@link RandomUtils#nextDouble(double, double)} returns a value within the
     * requested range [33, 42).
     */
    @Test
    void testNextDouble() {
        final double result = RandomUtils.nextDouble(33d, 42d);
        assertTrue(result >= 33d, "Result " + result + " should be >= 33");
        assertTrue(result < 42d, "Result " + result + " should be < 42");
    }

    /**
     * Tests that {@link RandomUtils#randomDouble(double, double)} returns a value within the
     * requested range [33, 42) for every provider in {@link #randomProvider()}.
     *
     * @param randomUtils the {@link RandomUtils} instance under test.
     */
    @ParameterizedTest
    @MethodSource("randomProvider")
    void testNextDouble(final RandomUtils randomUtils) {
        final double result = randomUtils.randomDouble(33d, 42d);
        assertTrue(result >= 33d, "Result " + result + " should be >= 33");
        assertTrue(result < 42d, "Result " + result + " should be < 42");
    }

    /**
     * Tests that {@link RandomUtils#nextDouble(double, double)} throws
     * {@link IllegalArgumentException} when the lower bound is greater than the upper bound.
     */
    @Test
    void testNextDoubleLowerGreaterUpper() {
        assertIllegalArgumentException(() -> RandomUtils.nextDouble(2, 1));
    }

    /**
     * Tests that {@link RandomUtils#randomDouble(double, double)} throws
     * {@link IllegalArgumentException} when the lower bound is greater than the upper bound,
     * for every provider in {@link #randomProvider()}.
     *
     * @param randomUtils the {@link RandomUtils} instance under test.
     */
    @ParameterizedTest
    @MethodSource("randomProvider")
    void testNextDoubleLowerGreaterUpper(final RandomUtils randomUtils) {
        assertIllegalArgumentException(() -> randomUtils.randomDouble(2, 1));
    }

    /**
     * Tests that {@link RandomUtils#nextDouble(double, double)} returns the boundary value
     * exactly when both bounds are equal (a degenerate range with no spread).
     */
    @Test
    void testNextDoubleMinimalRange() {
        assertEquals(42.1, RandomUtils.nextDouble(42.1, 42.1), DELTA);
    }

    /**
     * Tests that {@link RandomUtils#randomDouble(double, double)} returns the boundary value
     * exactly when both bounds are equal (a degenerate range with no spread),
     * for every provider in {@link #randomProvider()}.
     *
     * @param randomUtils the {@link RandomUtils} instance under test.
     */
    @ParameterizedTest
    @MethodSource("randomProvider")
    void testNextDoubleMinimalRange(final RandomUtils randomUtils) {
        assertEquals(42.1, randomUtils.randomDouble(42.1, 42.1), DELTA);
    }

    /**
     * Tests that {@link RandomUtils#nextDouble(double, double)} throws
     * {@link IllegalArgumentException} when the lower bound is negative.
     */
    @Test
    void testNextDoubleNegative() {
        assertIllegalArgumentException(() -> RandomUtils.nextDouble(-1, 1));
    }

    /**
     * Tests that {@link RandomUtils#randomDouble(double, double)} throws
     * {@link IllegalArgumentException} when the lower bound is negative,
     * for every provider in {@link #randomProvider()}.
     *
     * @param randomUtils the {@link RandomUtils} instance under test.
     */
    @ParameterizedTest
    @MethodSource("randomProvider")
    void testNextDoubleNegative(final RandomUtils randomUtils) {
        assertIllegalArgumentException(() -> randomUtils.randomDouble(-1, 1));
    }

    /**
     * Tests that the no-arg {@link RandomUtils#nextDouble()} returns a value in the full
     * valid range [0, Double.MAX_VALUE).
     */
    @Test
    void testNextDoubleRandomResult() {
        final double result = RandomUtils.nextDouble();
        assertTrue(result >= 0d, "Result " + result + " should be >= 0");
        assertTrue(result < Double.MAX_VALUE, "Result " + result + " should be < Double.MAX_VALUE");
    }

    /**
     * Tests that the no-arg {@link RandomUtils#randomDouble()} returns a value in the full
     * valid range [0, Double.MAX_VALUE) for every provider in {@link #randomProvider()}.
     *
     * @param randomUtils the {@link RandomUtils} instance under test.
     */
    @ParameterizedTest
    @MethodSource("randomProvider")
    void testNextDoubleRandomResult(final RandomUtils randomUtils) {
        final double result = randomUtils.randomDouble();
        assertTrue(result >= 0d, "Result " + result + " should be >= 0");
        assertTrue(result < Double.MAX_VALUE, "Result " + result + " should be < Double.MAX_VALUE");
    }

    /**
     * Tests extreme range for {@link RandomUtils#nextDouble(double, double)}: the result must
     * be within [0, Double.MAX_VALUE].
     */
    @Test
    void testExtremeRangeDouble() {
        final double result = RandomUtils.nextDouble(0, Double.MAX_VALUE);
        assertTrue(result >= 0 && result <= Double.MAX_VALUE); // TODO: should be <max?
    }

    /**
     * Tests extreme range for {@link RandomUtils#randomDouble(double, double)}: the result
     * must be within [0, Double.MAX_VALUE], for every provider in {@link #randomProvider()}.
     *
     * @param randomUtils the {@link RandomUtils} instance under test.
     */
    @ParameterizedTest
    @MethodSource("randomProvider")
    void testExtremeRangeDouble(final RandomUtils randomUtils) {
        final double result = randomUtils.randomDouble(0, Double.MAX_VALUE);
        assertTrue(result >= 0 && result <= Double.MAX_VALUE); // TODO: should be <max?
    }

    // -------------------------------------------------------------------------
    // Float
    // -------------------------------------------------------------------------

    /**
     * Tests that {@link RandomUtils#nextFloat(float, float)} returns a value within the
     * requested range [33, 42).
     */
    @Test
    void testNextFloat() {
        final float result = RandomUtils.nextFloat(33f, 42f);
        assertTrue(result >= 33f, "Result " + result + " should be >= 33");
        assertTrue(result < 42f, "Result " + result + " should be < 42");
    }

    /**
     * Tests that {@link RandomUtils#randomFloat(float, float)} returns a value within the
     * requested range [33, 42) for every provider in {@link #randomProvider()}.
     *
     * @param randomUtils the {@link RandomUtils} instance under test.
     */
    @ParameterizedTest
    @MethodSource("randomProvider")
    void testNextFloat(final RandomUtils randomUtils) {
        final float result = randomUtils.randomFloat(33f, 42f);
        assertTrue(result >= 33f, "Result " + result + " should be >= 33");
        assertTrue(result < 42f, "Result " + result + " should be < 42");
    }

    /**
     * Tests that {@link RandomUtils#nextFloat(float, float)} throws
     * {@link IllegalArgumentException} when the lower bound is greater than the upper bound.
     */
    @Test
    void testNextFloatLowerGreaterUpper() {
        assertIllegalArgumentException(() -> RandomUtils.nextFloat(2, 1));
    }

    /**
     * Tests that {@link RandomUtils#randomFloat(float, float)} throws
     * {@link IllegalArgumentException} when the lower bound is greater than the upper bound,
     * for every provider in {@link #randomProvider()}.
     *
     * @param randomUtils the {@link RandomUtils} instance under test.
     */
    @ParameterizedTest
    @MethodSource("randomProvider")
    void testNextFloatLowerGreaterUpper(final RandomUtils randomUtils) {
        assertIllegalArgumentException(() -> randomUtils.randomFloat(2, 1));
    }

    /**
     * Tests that {@link RandomUtils#nextFloat(float, float)} returns the boundary value
     * exactly when both bounds are equal (a degenerate range with no spread).
     */
    @Test
    void testNextFloatMinimalRange() {
        assertEquals(42.1f, RandomUtils.nextFloat(42.1f, 42.1f), DELTA);
    }

    /**
     * Tests that {@link RandomUtils#randomFloat(float, float)} returns the boundary value
     * exactly when both bounds are equal (a degenerate range with no spread),
     * for every provider in {@link #randomProvider()}.
     *
     * @param randomUtils the {@link RandomUtils} instance under test.
     */
    @ParameterizedTest
    @MethodSource("randomProvider")
    void testNextFloatMinimalRange(final RandomUtils randomUtils) {
        assertEquals(42.1f, randomUtils.randomFloat(42.1f, 42.1f), DELTA);
    }

    /**
     * Tests that {@link RandomUtils#nextFloat(float, float)} throws
     * {@link IllegalArgumentException} when the lower bound is negative.
     */
    @Test
    void testNextFloatNegative() {
        assertIllegalArgumentException(() -> RandomUtils.nextFloat(-1, 1));
    }

    /**
     * Tests that {@link RandomUtils#randomFloat(float, float)} throws
     * {@link IllegalArgumentException} when the lower bound is negative,
     * for every provider in {@link #randomProvider()}.
     *
     * @param randomUtils the {@link RandomUtils} instance under test.
     */
    @ParameterizedTest
    @MethodSource("randomProvider")
    void testNextFloatNegative(final RandomUtils randomUtils) {
        assertIllegalArgumentException(() -> randomUtils.randomFloat(-1, 1));
    }

    /**
     * Tests that the no-arg {@link RandomUtils#nextFloat()} returns a value in the full
     * valid range [0, Float.MAX_VALUE).
     */
    @Test
    void testNextFloatRandomResult() {
        final float result = RandomUtils.nextFloat();
        assertTrue(result >= 0f, "Result " + result + " should be >= 0");
        assertTrue(result < Float.MAX_VALUE, "Result " + result + " should be < Float.MAX_VALUE");
    }

    /**
     * Tests that the no-arg {@link RandomUtils#randomFloat()} returns a value in the full
     * valid range [0, Float.MAX_VALUE) for every provider in {@link #randomProvider()}.
     *
     * @param randomUtils the {@link RandomUtils} instance under test.
     */
    @ParameterizedTest
    @MethodSource("randomProvider")
    void testNextFloatRandomResult(final RandomUtils randomUtils) {
        final float result = randomUtils.randomFloat();
        assertTrue(result >= 0f, "Result " + result + " should be >= 0");
        assertTrue(result < Float.MAX_VALUE, "Result " + result + " should be < Float.MAX_VALUE");
    }

    /**
     * Tests extreme range for {@link RandomUtils#nextFloat(float, float)}: the result must
     * be within [0, Float.MAX_VALUE].
     */
    @Test
    void testExtremeRangeFloat() {
        final float result = RandomUtils.nextFloat(0, Float.MAX_VALUE);
        assertTrue(result >= 0f && result <= Float.MAX_VALUE); // TODO: should be <max?
    }

    /**
     * Tests extreme range for {@link RandomUtils#randomFloat(float, float)}: the result must
     * be within [0, Float.MAX_VALUE], for every provider in {@link #randomProvider()}.
     *
     * @param randomUtils the {@link RandomUtils} instance under test.
     */
    @ParameterizedTest
    @MethodSource("randomProvider")
    void testExtremeRangeFloat(final RandomUtils randomUtils) {
        final float result = randomUtils.randomFloat(0, Float.MAX_VALUE);
        assertTrue(result >= 0f && result <= Float.MAX_VALUE); // TODO: should be <max?
    }

    // -------------------------------------------------------------------------
    // Integer
    // -------------------------------------------------------------------------

    /**
     * Tests that {@link RandomUtils#nextInt(int, int)} returns a value within the
     * requested range [33, 42).
     */
    @Test
    void testNextInt() {
        final int result = RandomUtils.nextInt(33, 42);
        assertTrue(result >= 33, "Result " + result + " should be >= 33");
        assertTrue(result < 42, "Result " + result + " should be < 42");
    }

    /**
     * Tests that {@link RandomUtils#randomInt(int, int)} returns a value within the
     * requested range [33, 42) for every provider in {@link #randomProvider()}.
     *
     * @param randomUtils the {@link RandomUtils} instance under test.
     */
    @ParameterizedTest
    @MethodSource("randomProvider")
    void testNextInt(final RandomUtils randomUtils) {
        final int result = randomUtils.randomInt(33, 42);
        assertTrue(result >= 33, "Result " + result + " should be >= 33");
        assertTrue(result < 42, "Result " + result + " should be < 42");
    }

    /**
     * Tests that {@link RandomUtils#nextInt(int, int)} throws {@link IllegalArgumentException}
     * when the lower bound is greater than the upper bound.
     */
    @Test
    void testNextIntLowerGreaterUpper() {
        assertIllegalArgumentException(() -> RandomUtils.nextInt(2, 1));
    }

    /**
     * Tests that {@link RandomUtils#randomInt(int, int)} throws {@link IllegalArgumentException}
     * when the lower bound is greater than the upper bound,
     * for every provider in {@link #randomProvider()}.
     *
     * @param randomUtils the {@link RandomUtils} instance under test.
     */
    @ParameterizedTest
    @MethodSource("randomProvider")
    void testNextIntLowerGreaterUpper(final RandomUtils randomUtils) {
        assertIllegalArgumentException(() -> randomUtils.randomInt(2, 1));
    }

    /**
     * Tests that {@link RandomUtils#nextInt(int, int)} returns the boundary value exactly
     * when both bounds are equal (a degenerate range with no spread).
     */
    @Test
    void testNextIntMinimalRange() {
        assertEquals(42, RandomUtils.nextInt(42, 42));
    }

    /**
     * Tests that {@link RandomUtils#randomInt(int, int)} returns the boundary value exactly
     * when both bounds are equal (a degenerate range with no spread),
     * for every provider in {@link #randomProvider()}.
     *
     * @param randomUtils the {@link RandomUtils} instance under test.
     */
    @ParameterizedTest
    @MethodSource("randomProvider")
    void testNextIntMinimalRange(final RandomUtils randomUtils) {
        assertEquals(42, randomUtils.randomInt(42, 42));
    }

    /**
     * Tests that {@link RandomUtils#nextInt(int, int)} throws {@link IllegalArgumentException}
     * when the lower bound is negative.
     */
    @Test
    void testNextIntNegative() {
        assertIllegalArgumentException(() -> RandomUtils.nextInt(-1, 1));
    }

    /**
     * Tests that {@link RandomUtils#randomInt(int, int)} throws {@link IllegalArgumentException}
     * when the lower bound is negative, for every provider in {@link #randomProvider()}.
     *
     * @param randomUtils the {@link RandomUtils} instance under test.
     */
    @ParameterizedTest
    @MethodSource("randomProvider")
    void testNextIntNegative(final RandomUtils randomUtils) {
        assertIllegalArgumentException(() -> randomUtils.randomInt(-1, 1));
    }

    /**
     * Tests that the no-arg {@link RandomUtils#nextInt()} returns a positive value strictly
     * less than {@link Integer#MAX_VALUE}.
     */
    @Test
    void testNextIntRandomResult() {
        final int randomResult = RandomUtils.nextInt();
        assertTrue(randomResult > 0, "Result " + randomResult + " should be > 0");
        assertTrue(randomResult < Integer.MAX_VALUE, "Result " + randomResult + " should be < Integer.MAX_VALUE");
    }

    /**
     * Tests that the no-arg {@link RandomUtils#randomInt()} returns a positive value strictly
     * less than {@link Integer#MAX_VALUE}, for every provider in {@link #randomProvider()}.
     *
     * @param randomUtils the {@link RandomUtils} instance under test.
     */
    @ParameterizedTest
    @MethodSource("randomProvider")
    void testNextIntRandomResult(final RandomUtils randomUtils) {
        final int randomResult = randomUtils.randomInt();
        assertTrue(randomResult > 0, "Result " + randomResult + " should be > 0");
        assertTrue(randomResult < Integer.MAX_VALUE, "Result " + randomResult + " should be < Integer.MAX_VALUE");
    }

    /**
     * Tests extreme range for {@link RandomUtils#nextInt(int, int)}: the result must be in
     * [0, Integer.MAX_VALUE).
     */
    @Test
    void testExtremeRangeInt() {
        final int result = RandomUtils.nextInt(0, Integer.MAX_VALUE);
        assertTrue(result >= 0, "Result " + result + " should be >= 0");
        assertTrue(result < Integer.MAX_VALUE, "Result " + result + " should be < Integer.MAX_VALUE");
    }

    /**
     * Tests extreme range for {@link RandomUtils#randomInt(int, int)}: the result must be in
     * [0, Integer.MAX_VALUE), for every provider in {@link #randomProvider()}.
     *
     * @param randomUtils the {@link RandomUtils} instance under test.
     */
    @ParameterizedTest
    @MethodSource("randomProvider")
    void testExtremeRangeInt(final RandomUtils randomUtils) {
        final int result = randomUtils.randomInt(0, Integer.MAX_VALUE);
        assertTrue(result >= 0, "Result " + result + " should be >= 0");
        assertTrue(result < Integer.MAX_VALUE, "Result " + result + " should be < Integer.MAX_VALUE");
    }

    // -------------------------------------------------------------------------
    // Long
    // -------------------------------------------------------------------------

    /**
     * Tests that {@link RandomUtils#nextLong(long, long)} returns a value within the
     * requested range [33, 42).
     */
    @Test
    void testNextLong() {
        final long result = RandomUtils.nextLong(33L, 42L);
        assertTrue(result >= 33L, "Result " + result + " should be >= 33");
        assertTrue(result < 42L, "Result " + result + " should be < 42");
    }

    /**
     * Tests that {@link RandomUtils#randomLong(long, long)} returns a value within the
     * requested range [33, 42) for every provider in {@link #randomProvider()}.
     *
     * @param randomUtils the {@link RandomUtils} instance under test.
     */
    @ParameterizedTest
    @MethodSource("randomProvider")
    void testNextLong(final RandomUtils randomUtils) {
        final long result = randomUtils.randomLong(33L, 42L);
        assertTrue(result >= 33L, "Result " + result + " should be >= 33");
        assertTrue(result < 42L, "Result " + result + " should be < 42");
    }

    /**
     * Tests that {@link RandomUtils#nextLong(long, long)} throws {@link IllegalArgumentException}
     * when the lower bound is greater than the upper bound.
     */
    @Test
    void testNextLongLowerGreaterUpper() {
        assertIllegalArgumentException(() -> RandomUtils.nextLong(2, 1));
    }

    /**
     * Tests that {@link RandomUtils#randomLong(long, long)} throws {@link IllegalArgumentException}
     * when the lower bound is greater than the upper bound,
     * for every provider in {@link #randomProvider()}.
     *
     * @param randomUtils the {@link RandomUtils} instance under test.
     */
    @ParameterizedTest
    @MethodSource("randomProvider")
    void testNextLongLowerGreaterUpper(final RandomUtils randomUtils) {
        assertIllegalArgumentException(() -> randomUtils.randomLong(2, 1));
    }

    /**
     * Tests that {@link RandomUtils#nextLong(long, long)} returns the boundary value exactly
     * when both bounds are equal (a degenerate range with no spread).
     */
    @Test
    void testNextLongMinimalRange() {
        assertEquals(42L, RandomUtils.nextLong(42L, 42L));
    }

    /**
     * Tests that {@link RandomUtils#randomLong(long, long)} returns the boundary value exactly
     * when both bounds are equal (a degenerate range with no spread),
     * for every provider in {@link #randomProvider()}.
     *
     * @param randomUtils the {@link RandomUtils} instance under test.
     */
    @ParameterizedTest
    @MethodSource("randomProvider")
    void testNextLongMinimalRange(final RandomUtils randomUtils) {
        assertEquals(42L, randomUtils.randomLong(42L, 42L));
    }

    /**
     * Tests that {@link RandomUtils#nextLong(long, long)} throws {@link IllegalArgumentException}
     * when the lower bound is negative.
     */
    @Test
    void testNextLongNegative() {
        assertIllegalArgumentException(() -> RandomUtils.nextLong(-1, 1));
    }

    /**
     * Tests that {@link RandomUtils#randomLong(long, long)} throws {@link IllegalArgumentException}
     * when the lower bound is negative, for every provider in {@link #randomProvider()}.
     *
     * @param randomUtils the {@link RandomUtils} instance under test.
     */
    @ParameterizedTest
    @MethodSource("randomProvider")
    void testNextLongNegative(final RandomUtils randomUtils) {
        assertIllegalArgumentException(() -> randomUtils.randomLong(-1, 1));
    }

    /**
     * Tests that the no-arg {@link RandomUtils#nextLong()} returns a non-negative value
     * strictly less than {@link Long#MAX_VALUE}.
     */
    @Test
    void testNextLongRandomResult() {
        final long result = RandomUtils.nextLong();
        assertTrue(result >= 0L, "Result " + result + " should be >= 0");
        assertTrue(result < Long.MAX_VALUE, "Result " + result + " should be < Long.MAX_VALUE");
    }

    /**
     * Tests that the no-arg {@link RandomUtils#randomLong()} returns a non-negative value
     * strictly less than {@link Long#MAX_VALUE}, for every provider in {@link #randomProvider()}.
     *
     * @param randomUtils the {@link RandomUtils} instance under test.
     */
    @ParameterizedTest
    @MethodSource("randomProvider")
    void testNextLongRandomResult(final RandomUtils randomUtils) {
        final long result = randomUtils.randomLong();
        assertTrue(result >= 0L, "Result " + result + " should be >= 0");
        assertTrue(result < Long.MAX_VALUE, "Result " + result + " should be < Long.MAX_VALUE");
    }

    /**
     * Tests extreme range for {@link RandomUtils#nextLong(long, long)}: the result must be
     * in [0, Long.MAX_VALUE).
     */
    @Test
    void testExtremeRangeLong() {
        final long result = RandomUtils.nextLong(0, Long.MAX_VALUE);
        assertTrue(result >= 0, "Result " + result + " should be >= 0");
        assertTrue(result < Long.MAX_VALUE, "Result " + result + " should be < Long.MAX_VALUE");
    }

    /**
     * Tests extreme range for {@link RandomUtils#randomLong(long, long)}: the result must be
     * in [0, Long.MAX_VALUE), for every provider in {@link #randomProvider()}.
     *
     * @param randomUtils the {@link RandomUtils} instance under test.
     */
    @ParameterizedTest
    @MethodSource("randomProvider")
    void testExtremeRangeLong(final RandomUtils randomUtils) {
        final long result = randomUtils.randomLong(0, Long.MAX_VALUE);
        assertTrue(result >= 0, "Result " + result + " should be >= 0");
        assertTrue(result < Long.MAX_VALUE, "Result " + result + " should be < Long.MAX_VALUE");
    }

    /**
     * Regression test for LANG-1592: a previous implementation cast the result of
     * {@code nextDouble(startInclusive, endExclusive)} to {@code long}, which could
     * occasionally produce a value equal to the exclusive upper bound due to floating-point
     * rounding.
     *
     * <p>The range [12900000000001, 12900000000016) is chosen because the large magnitude
     * amplifies floating-point errors; the loop size is large enough that the old
     * implementation would fail on most runs.</p>
     *
     * <pre>
     * return (long) nextDouble(startInclusive, endExclusive); // WRONG: may equal endExclusive
     * </pre>
     *
     * <p>See LANG-1592.</p>
     */
    @Test
    void testLargeValueRangeLong() {
        final long startInclusive = 12900000000001L;
        final long endExclusive = 12900000000016L;
        // Note: The method using 'return (long) nextDouble(startInclusive, endExclusive)'
        // takes thousands of calls to generate an error. This size loop fails most
        // of the time with the previous method.
        final int n = (int) (endExclusive - startInclusive) * 1000;
        for (int i = 0; i < n; i++) {
            assertNotEquals(endExclusive, RandomUtils.nextLong(startInclusive, endExclusive));
        }
    }

    /**
     * Regression test for LANG-1592 (see {@link #testLargeValueRangeLong()}), exercised
     * for every provider in {@link #randomProvider()}.
     *
     * @param randomUtils the {@link RandomUtils} instance under test.
     */
    @ParameterizedTest
    @MethodSource("randomProvider")
    void testLargeValueRangeLong(final RandomUtils randomUtils) {
        final long startInclusive = 12900000000001L;
        final long endExclusive = 12900000000016L;
        // Note: The method using 'return (long) nextDouble(startInclusive, endExclusive)'
        // takes thousands of calls to generate an error. This size loop fails most
        // of the time with the previous method.
        final int n = (int) (endExclusive - startInclusive) * 1000;
        for (int i = 0; i < n; i++) {
            assertNotEquals(endExclusive, randomUtils.randomLong(startInclusive, endExclusive));
        }
    }
}
