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
 * <p>The key behaviour under test is the IEEE-754r rule: {@code NaN} values are
 * ignored when computing a min/max, and {@code NaN} is only returned when every
 * input value is {@code NaN}.</p>
 */
class IEEE754rUtilsTest extends AbstractLangTest {

    /** Tolerance used when comparing floating-point results. */
    private static final double DELTA = 0.01;

    @Test
    void testConstructorExists() {
        // The constructor is deprecated but still public; ensure it can be invoked.
        new IEEE754rUtils();
    }

    @Test
    void testNullArrayThrowsNullPointerException() {
        assertNullPointerException(() -> IEEE754rUtils.min((float[]) null), "NullPointerException expected for null input");
        assertNullPointerException(() -> IEEE754rUtils.max((float[]) null), "NullPointerException expected for null input");
        assertNullPointerException(() -> IEEE754rUtils.min((double[]) null), "NullPointerException expected for null input");
        assertNullPointerException(() -> IEEE754rUtils.max((double[]) null), "NullPointerException expected for null input");
    }

    @Test
    void testEmptyArrayThrowsIllegalArgumentException() {
        // Calling min()/max() with no varargs passes an empty array.
        assertIllegalArgumentException(IEEE754rUtils::min, "IllegalArgumentException expected for empty input");
        assertIllegalArgumentException(IEEE754rUtils::max, "IllegalArgumentException expected for empty input");
        assertIllegalArgumentException(IEEE754rUtils::min, "IllegalArgumentException expected for empty input");
        assertIllegalArgumentException(IEEE754rUtils::max, "IllegalArgumentException expected for empty input");
    }

    @Test
    void testThreeArgMinMaxDoubleIgnoresNaN() {
        // NaN is skipped, so min/max are taken over the remaining real values.
        assertEquals(1.2, IEEE754rUtils.min(1.2, 2.5, Double.NaN), DELTA);
        assertEquals(2.5, IEEE754rUtils.max(1.2, 2.5, Double.NaN), DELTA);
    }

    @Test
    void testThreeArgMaxDoubleAllNaNReturnsNaN() {
        // When every value is NaN, the result must also be NaN.
        assertTrue(Double.isNaN(IEEE754rUtils.max(Double.NaN, Double.NaN, Double.NaN)));
    }

    @Test
    void testThreeArgMinMaxFloatIgnoresNaN() {
        // NaN is skipped, so min/max are taken over the remaining real values.
        assertEquals(1.2f, IEEE754rUtils.min(1.2f, 2.5f, Float.NaN), DELTA);
        assertEquals(2.5f, IEEE754rUtils.max(1.2f, 2.5f, Float.NaN), DELTA);
    }

    @Test
    void testThreeArgMaxFloatAllNaNReturnsNaN() {
        // When every value is NaN, the result must also be NaN.
        assertTrue(Float.isNaN(IEEE754rUtils.max(Float.NaN, Float.NaN, Float.NaN)));
    }

    @Test
    void testDoubleArrayIgnoresNaN() {
        // NaN entries (including a leading one) are ignored by min/max.
        final double[] withoutLeadingNaN = { 1.2, Double.NaN, 3.7, 27.0, 42.0, Double.NaN };
        assertEquals(42.0, IEEE754rUtils.max(withoutLeadingNaN), DELTA);
        assertEquals(1.2, IEEE754rUtils.min(withoutLeadingNaN), DELTA);

        final double[] withLeadingNaN = { Double.NaN, 1.2, Double.NaN, 3.7, 27.0, 42.0, Double.NaN };
        assertEquals(42.0, IEEE754rUtils.max(withLeadingNaN), DELTA);
        assertEquals(1.2, IEEE754rUtils.min(withLeadingNaN), DELTA);
    }

    @Test
    void testFloatArrayIgnoresNaN() {
        // NaN entries (including a leading one) are ignored by min/max.
        final float[] withoutLeadingNaN = { 1.2f, Float.NaN, 3.7f, 27.0f, 42.0f, Float.NaN };
        assertEquals(1.2f, IEEE754rUtils.min(withoutLeadingNaN), DELTA);
        assertEquals(42.0f, IEEE754rUtils.max(withoutLeadingNaN), DELTA);

        final float[] withLeadingNaN = { Float.NaN, 1.2f, Float.NaN, 3.7f, 27.0f, 42.0f, Float.NaN };
        assertEquals(1.2f, IEEE754rUtils.min(withLeadingNaN), DELTA);
        assertEquals(42.0f, IEEE754rUtils.max(withLeadingNaN), DELTA);
    }

}
