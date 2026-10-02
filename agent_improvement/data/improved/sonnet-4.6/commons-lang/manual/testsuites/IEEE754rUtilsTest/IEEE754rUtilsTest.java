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
package org.apache.commons.lang3.math;

import static org.apache.commons.lang3.LangAssertions.assertIllegalArgumentException;
import static org.apache.commons.lang3.LangAssertions.assertNullPointerException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link IEEE754rUtils}.
 *
 * <p>IEEE-754r specifies that NaN is treated as a "missing value" in min/max comparisons:
 * when at least one non-NaN operand is present, the result is that non-NaN value.
 * NaN is only propagated when every operand is NaN.</p>
 */
class IEEE754rUtilsTest extends AbstractLangTest {

    /** Tolerance used for floating-point equality assertions. */
    private static final double DELTA = 0.01;

    @Test
    void testConstructorExists() {
        new IEEE754rUtils();
    }

    @Test
    void testEnforceExceptions() {
        assertNullPointerException(() -> IEEE754rUtils.min((float[]) null), "NullPointerException expected for null float array");
        assertIllegalArgumentException(IEEE754rUtils::min, "IllegalArgumentException expected for empty float array");
        assertNullPointerException(() -> IEEE754rUtils.max((float[]) null), "NullPointerException expected for null float array");
        assertIllegalArgumentException(IEEE754rUtils::max, "IllegalArgumentException expected for empty float array");
        assertNullPointerException(() -> IEEE754rUtils.min((double[]) null), "NullPointerException expected for null double array");
        assertIllegalArgumentException(IEEE754rUtils::min, "IllegalArgumentException expected for empty double array");
        assertNullPointerException(() -> IEEE754rUtils.max((double[]) null), "NullPointerException expected for null double array");
        assertIllegalArgumentException(IEEE754rUtils::max, "IllegalArgumentException expected for empty double array");
    }

    // --- 3-value double min/max: NaN is skipped when non-NaN values are present ---

    @Test
    void testDoubleMinThreeValuesIgnoresNaN() {
        assertEquals(1.2, IEEE754rUtils.min(1.2, 2.5, Double.NaN), DELTA);
    }

    @Test
    void testDoubleMaxThreeValuesIgnoresNaN() {
        assertEquals(2.5, IEEE754rUtils.max(1.2, 2.5, Double.NaN), DELTA);
    }

    @Test
    void testDoubleMaxAllNaNReturnsNaN() {
        assertTrue(Double.isNaN(IEEE754rUtils.max(Double.NaN, Double.NaN, Double.NaN)));
    }

    // --- 3-value float min/max: NaN is skipped when non-NaN values are present ---

    @Test
    void testFloatMinThreeValuesIgnoresNaN() {
        assertEquals(1.2f, IEEE754rUtils.min(1.2f, 2.5f, Float.NaN), DELTA);
    }

    @Test
    void testFloatMaxThreeValuesIgnoresNaN() {
        assertEquals(2.5f, IEEE754rUtils.max(1.2f, 2.5f, Float.NaN), DELTA);
    }

    @Test
    void testFloatMaxAllNaNReturnsNaN() {
        assertTrue(Float.isNaN(IEEE754rUtils.max(Float.NaN, Float.NaN, Float.NaN)));
    }

    // --- double array min/max: NaN values at arbitrary positions are skipped ---

    @Test
    void testDoubleArrayMinMaxIgnoresInterspersedNaN() {
        // NaN appears in the middle and at the end; non-NaN range is [1.2, 42.0]
        final double[] a = { 1.2, Double.NaN, 3.7, 27.0, 42.0, Double.NaN };
        assertEquals(42.0, IEEE754rUtils.max(a), DELTA);
        assertEquals(1.2, IEEE754rUtils.min(a), DELTA);
    }

    @Test
    void testDoubleArrayMinMaxIgnoresLeadingAndTrailingNaN() {
        // NaN appears at the start, middle, and end of the array
        final double[] b = { Double.NaN, 1.2, Double.NaN, 3.7, 27.0, 42.0, Double.NaN };
        assertEquals(42.0, IEEE754rUtils.max(b), DELTA);
        assertEquals(1.2, IEEE754rUtils.min(b), DELTA);
    }

    // --- float array min/max: NaN values at arbitrary positions are skipped ---

    @Test
    void testFloatArrayMinMaxIgnoresInterspersedNaN() {
        // NaN appears in the middle and at the end; non-NaN range is [1.2, 42.0]
        final float[] aF = { 1.2f, Float.NaN, 3.7f, 27.0f, 42.0f, Float.NaN };
        assertEquals(1.2f, IEEE754rUtils.min(aF), DELTA);
        assertEquals(42.0f, IEEE754rUtils.max(aF), DELTA);
    }

    @Test
    void testFloatArrayMinMaxIgnoresLeadingAndTrailingNaN() {
        // NaN appears at the start, middle, and end of the array
        final float[] bF = { Float.NaN, 1.2f, Float.NaN, 3.7f, 27.0f, 42.0f, Float.NaN };
        assertEquals(1.2f, IEEE754rUtils.min(bF), DELTA);
        assertEquals(42.0f, IEEE754rUtils.max(bF), DELTA);
    }

}
