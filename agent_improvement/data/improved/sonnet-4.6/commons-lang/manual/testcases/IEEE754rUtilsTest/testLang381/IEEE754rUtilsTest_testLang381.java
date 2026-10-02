package org.apache.commons.lang3.math;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

/**
 * Tests that IEEE754rUtils min/max treat NaN as a missing value rather than
 * propagating it — a non-NaN operand always wins per IEEE-754r semantics.
 */
public class IEEE754rUtilsTest_testLang381 extends AbstractLangTest {

    private static final double DELTA = 0.01;

    // -----------------------------------------------------------------------
    // double three-argument variants
    // -----------------------------------------------------------------------

    @Test
    void testDoubleMinIgnoresNaN() {
        assertEquals(1.2, IEEE754rUtils.min(1.2, 2.5, Double.NaN), DELTA);
    }

    @Test
    void testDoubleMaxIgnoresNaN() {
        assertEquals(2.5, IEEE754rUtils.max(1.2, 2.5, Double.NaN), DELTA);
    }

    @Test
    void testDoubleMaxAllNaNReturnsNaN() {
        assertTrue(Double.isNaN(IEEE754rUtils.max(Double.NaN, Double.NaN, Double.NaN)));
    }

    // -----------------------------------------------------------------------
    // float three-argument variants
    // -----------------------------------------------------------------------

    @Test
    void testFloatMinIgnoresNaN() {
        assertEquals(1.2f, IEEE754rUtils.min(1.2f, 2.5f, Float.NaN), DELTA);
    }

    @Test
    void testFloatMaxIgnoresNaN() {
        assertEquals(2.5f, IEEE754rUtils.max(1.2f, 2.5f, Float.NaN), DELTA);
    }

    @Test
    void testFloatMaxAllNaNReturnsNaN() {
        assertTrue(Float.isNaN(IEEE754rUtils.max(Float.NaN, Float.NaN, Float.NaN)));
    }

    // -----------------------------------------------------------------------
    // double array variants — NaN interspersed but not at the boundaries
    // -----------------------------------------------------------------------

    @Test
    void testDoubleArrayMaxIgnoresInterspersedNaN() {
        final double[] values = { 1.2, Double.NaN, 3.7, 27.0, 42.0, Double.NaN };
        assertEquals(42.0, IEEE754rUtils.max(values), DELTA);
    }

    @Test
    void testDoubleArrayMinIgnoresInterspersedNaN() {
        final double[] values = { 1.2, Double.NaN, 3.7, 27.0, 42.0, Double.NaN };
        assertEquals(1.2, IEEE754rUtils.min(values), DELTA);
    }

    // -----------------------------------------------------------------------
    // double array variants — NaN also at the leading and trailing positions
    // -----------------------------------------------------------------------

    @Test
    void testDoubleArrayMaxIgnoresLeadingAndTrailingNaN() {
        final double[] values = { Double.NaN, 1.2, Double.NaN, 3.7, 27.0, 42.0, Double.NaN };
        assertEquals(42.0, IEEE754rUtils.max(values), DELTA);
    }

    @Test
    void testDoubleArrayMinIgnoresLeadingAndTrailingNaN() {
        final double[] values = { Double.NaN, 1.2, Double.NaN, 3.7, 27.0, 42.0, Double.NaN };
        assertEquals(1.2, IEEE754rUtils.min(values), DELTA);
    }

    // -----------------------------------------------------------------------
    // float array variants — NaN interspersed but not at the boundaries
    // -----------------------------------------------------------------------

    @Test
    void testFloatArrayMinIgnoresInterspersedNaN() {
        final float[] values = { 1.2f, Float.NaN, 3.7f, 27.0f, 42.0f, Float.NaN };
        assertEquals(1.2f, IEEE754rUtils.min(values), DELTA);
    }

    @Test
    void testFloatArrayMaxIgnoresInterspersedNaN() {
        final float[] values = { 1.2f, Float.NaN, 3.7f, 27.0f, 42.0f, Float.NaN };
        assertEquals(42.0f, IEEE754rUtils.max(values), DELTA);
    }

    // -----------------------------------------------------------------------
    // float array variants — NaN also at the leading and trailing positions
    // -----------------------------------------------------------------------

    @Test
    void testFloatArrayMinIgnoresLeadingAndTrailingNaN() {
        final float[] values = { Float.NaN, 1.2f, Float.NaN, 3.7f, 27.0f, 42.0f, Float.NaN };
        assertEquals(1.2f, IEEE754rUtils.min(values), DELTA);
    }

    @Test
    void testFloatArrayMaxIgnoresLeadingAndTrailingNaN() {
        final float[] values = { Float.NaN, 1.2f, Float.NaN, 3.7f, 27.0f, 42.0f, Float.NaN };
        assertEquals(42.0f, IEEE754rUtils.max(values), DELTA);
    }
}
