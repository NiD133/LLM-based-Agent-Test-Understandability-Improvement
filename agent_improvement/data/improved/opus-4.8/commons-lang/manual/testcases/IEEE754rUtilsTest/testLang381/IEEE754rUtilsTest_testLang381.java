package org.apache.commons.lang3.math;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

/**
 * Tests the IEEE-754r behaviour of {@link IEEE754rUtils} (LANG-381).
 *
 * <p>The defining rule under test: when computing a minimum or maximum,
 * {@code NaN} values are ignored, and {@code NaN} is only returned when
 * <em>every</em> input is {@code NaN}.</p>
 */
public class IEEE754rUtilsTest_testLang381 extends AbstractLangTest {

    /** Tolerance used when comparing floating-point results. */
    private static final double DELTA = 0.01;

    // --- double: three-argument min / max -----------------------------------

    @Test
    void minOfThreeDoublesIgnoresNaN() {
        assertEquals(1.2, IEEE754rUtils.min(1.2, 2.5, Double.NaN), DELTA);
    }

    @Test
    void maxOfThreeDoublesIgnoresNaN() {
        assertEquals(2.5, IEEE754rUtils.max(1.2, 2.5, Double.NaN), DELTA);
    }

    @Test
    void maxOfThreeDoublesReturnsNaNWhenAllNaN() {
        assertTrue(Double.isNaN(IEEE754rUtils.max(Double.NaN, Double.NaN, Double.NaN)));
    }

    // --- float: three-argument min / max ------------------------------------

    @Test
    void minOfThreeFloatsIgnoresNaN() {
        assertEquals(1.2f, IEEE754rUtils.min(1.2f, 2.5f, Float.NaN), DELTA);
    }

    @Test
    void maxOfThreeFloatsIgnoresNaN() {
        assertEquals(2.5f, IEEE754rUtils.max(1.2f, 2.5f, Float.NaN), DELTA);
    }

    @Test
    void maxOfThreeFloatsReturnsNaNWhenAllNaN() {
        assertTrue(Float.isNaN(IEEE754rUtils.max(Float.NaN, Float.NaN, Float.NaN)));
    }

    // --- double[]: array min / max, NaN interspersed and leading ------------

    @Test
    void doubleArrayMinMaxIgnoreInterspersedNaN() {
        final double[] valuesWithNaN = { 1.2, Double.NaN, 3.7, 27.0, 42.0, Double.NaN };
        assertEquals(42.0, IEEE754rUtils.max(valuesWithNaN), DELTA);
        assertEquals(1.2, IEEE754rUtils.min(valuesWithNaN), DELTA);
    }

    @Test
    void doubleArrayMinMaxIgnoreLeadingNaN() {
        final double[] valuesWithLeadingNaN = { Double.NaN, 1.2, Double.NaN, 3.7, 27.0, 42.0, Double.NaN };
        assertEquals(42.0, IEEE754rUtils.max(valuesWithLeadingNaN), DELTA);
        assertEquals(1.2, IEEE754rUtils.min(valuesWithLeadingNaN), DELTA);
    }

    // --- float[]: array min / max, NaN interspersed and leading -------------

    @Test
    void floatArrayMinMaxIgnoreInterspersedNaN() {
        final float[] valuesWithNaN = { 1.2f, Float.NaN, 3.7f, 27.0f, 42.0f, Float.NaN };
        assertEquals(1.2f, IEEE754rUtils.min(valuesWithNaN), DELTA);
        assertEquals(42.0f, IEEE754rUtils.max(valuesWithNaN), DELTA);
    }

    @Test
    void floatArrayMinMaxIgnoreLeadingNaN() {
        final float[] valuesWithLeadingNaN = { Float.NaN, 1.2f, Float.NaN, 3.7f, 27.0f, 42.0f, Float.NaN };
        assertEquals(1.2f, IEEE754rUtils.min(valuesWithLeadingNaN), DELTA);
        assertEquals(42.0f, IEEE754rUtils.max(valuesWithLeadingNaN), DELTA);
    }
}
