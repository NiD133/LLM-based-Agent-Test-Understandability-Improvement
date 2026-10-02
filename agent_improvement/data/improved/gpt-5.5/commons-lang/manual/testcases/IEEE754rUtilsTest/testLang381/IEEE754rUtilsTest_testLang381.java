package org.apache.commons.lang3.math;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

public class IEEE754rUtilsTest_testLang381 extends AbstractLangTest {

    @Test
    void testLang381() {
        assertDoubleThreeArgumentMethodsIgnoreTrailingNaN();
        assertFloatThreeArgumentMethodsIgnoreTrailingNaN();
        assertDoubleArrayMethodsIgnoreNaNValues();
        assertFloatArrayMethodsIgnoreNaNValues();
    }

    private void assertDoubleThreeArgumentMethodsIgnoreTrailingNaN() {
        assertEquals(1.2, IEEE754rUtils.min(1.2, 2.5, Double.NaN), 0.01);
        assertEquals(2.5, IEEE754rUtils.max(1.2, 2.5, Double.NaN), 0.01);
        assertTrue(Double.isNaN(IEEE754rUtils.max(Double.NaN, Double.NaN, Double.NaN)));
    }

    private void assertFloatThreeArgumentMethodsIgnoreTrailingNaN() {
        assertEquals(1.2f, IEEE754rUtils.min(1.2f, 2.5f, Float.NaN), 0.01);
        assertEquals(2.5f, IEEE754rUtils.max(1.2f, 2.5f, Float.NaN), 0.01);
        assertTrue(Float.isNaN(IEEE754rUtils.max(Float.NaN, Float.NaN, Float.NaN)));
    }

    private void assertDoubleArrayMethodsIgnoreNaNValues() {
        final double[] valuesWithEmbeddedNaNs = { 1.2, Double.NaN, 3.7, 27.0, 42.0, Double.NaN };
        assertEquals(42.0, IEEE754rUtils.max(valuesWithEmbeddedNaNs), 0.01);
        assertEquals(1.2, IEEE754rUtils.min(valuesWithEmbeddedNaNs), 0.01);

        final double[] valuesStartingWithNaN = { Double.NaN, 1.2, Double.NaN, 3.7, 27.0, 42.0, Double.NaN };
        assertEquals(42.0, IEEE754rUtils.max(valuesStartingWithNaN), 0.01);
        assertEquals(1.2, IEEE754rUtils.min(valuesStartingWithNaN), 0.01);
    }

    private void assertFloatArrayMethodsIgnoreNaNValues() {
        final float[] valuesWithEmbeddedNaNs = { 1.2f, Float.NaN, 3.7f, 27.0f, 42.0f, Float.NaN };
        assertEquals(1.2f, IEEE754rUtils.min(valuesWithEmbeddedNaNs), 0.01);
        assertEquals(42.0f, IEEE754rUtils.max(valuesWithEmbeddedNaNs), 0.01);

        final float[] valuesStartingWithNaN = { Float.NaN, 1.2f, Float.NaN, 3.7f, 27.0f, 42.0f, Float.NaN };
        assertEquals(1.2f, IEEE754rUtils.min(valuesStartingWithNaN), 0.01);
        assertEquals(42.0f, IEEE754rUtils.max(valuesStartingWithNaN), 0.01);
    }
}
