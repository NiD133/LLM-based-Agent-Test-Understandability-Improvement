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
 */
class IEEE754rUtilsTest extends AbstractLangTest {

    private static final double DOUBLE_TOLERANCE = 0.01;
    private static final float FLOAT_TOLERANCE = 0.01f;

    @Test
    void testConstructorExists() {
        new IEEE754rUtils();
    }

    @Test
    void testEnforceExceptions() {
        assertFloatMinRejectsNullAndEmptyArrays();
        assertFloatMaxRejectsNullAndEmptyArrays();
        assertDoubleMinRejectsNullAndEmptyArrays();
        assertDoubleMaxRejectsNullAndEmptyArrays();
    }

    private void assertFloatMinRejectsNullAndEmptyArrays() {
        assertNullPointerException(() -> IEEE754rUtils.min((float[]) null), "IllegalArgumentException expected for null input");
        assertIllegalArgumentException(IEEE754rUtils::min, "IllegalArgumentException expected for empty input");
    }

    private void assertFloatMaxRejectsNullAndEmptyArrays() {
        assertNullPointerException(() -> IEEE754rUtils.max((float[]) null), "IllegalArgumentException expected for null input");
        assertIllegalArgumentException(IEEE754rUtils::max, "IllegalArgumentException expected for empty input");
    }

    private void assertDoubleMinRejectsNullAndEmptyArrays() {
        assertNullPointerException(() -> IEEE754rUtils.min((double[]) null), "IllegalArgumentException expected for null input");
        assertIllegalArgumentException(IEEE754rUtils::min, "IllegalArgumentException expected for empty input");
    }

    private void assertDoubleMaxRejectsNullAndEmptyArrays() {
        assertNullPointerException(() -> IEEE754rUtils.max((double[]) null), "IllegalArgumentException expected for null input");
        assertIllegalArgumentException(IEEE754rUtils::max, "IllegalArgumentException expected for empty input");
    }

    @Test
    void testLang381() {
        assertDoubleOperationsIgnoreMixedNaNValues();
        assertFloatOperationsIgnoreMixedNaNValues();
        assertDoubleArrayOperationsIgnoreMixedNaNValues();
        assertFloatArrayOperationsIgnoreMixedNaNValues();
    }

    private void assertDoubleOperationsIgnoreMixedNaNValues() {
        assertEquals(1.2, IEEE754rUtils.min(1.2, 2.5, Double.NaN), DOUBLE_TOLERANCE);
        assertEquals(2.5, IEEE754rUtils.max(1.2, 2.5, Double.NaN), DOUBLE_TOLERANCE);
        assertTrue(Double.isNaN(IEEE754rUtils.max(Double.NaN, Double.NaN, Double.NaN)));
    }

    private void assertFloatOperationsIgnoreMixedNaNValues() {
        assertEquals(1.2f, IEEE754rUtils.min(1.2f, 2.5f, Float.NaN), FLOAT_TOLERANCE);
        assertEquals(2.5f, IEEE754rUtils.max(1.2f, 2.5f, Float.NaN), FLOAT_TOLERANCE);
        assertTrue(Float.isNaN(IEEE754rUtils.max(Float.NaN, Float.NaN, Float.NaN)));
    }

    private void assertDoubleArrayOperationsIgnoreMixedNaNValues() {
        final double[] valuesWithNaNAfterFirstValue = { 1.2, Double.NaN, 3.7, 27.0, 42.0, Double.NaN };
        assertEquals(42.0, IEEE754rUtils.max(valuesWithNaNAfterFirstValue), DOUBLE_TOLERANCE);
        assertEquals(1.2, IEEE754rUtils.min(valuesWithNaNAfterFirstValue), DOUBLE_TOLERANCE);

        final double[] valuesStartingWithNaN = { Double.NaN, 1.2, Double.NaN, 3.7, 27.0, 42.0, Double.NaN };
        assertEquals(42.0, IEEE754rUtils.max(valuesStartingWithNaN), DOUBLE_TOLERANCE);
        assertEquals(1.2, IEEE754rUtils.min(valuesStartingWithNaN), DOUBLE_TOLERANCE);
    }

    private void assertFloatArrayOperationsIgnoreMixedNaNValues() {
        final float[] valuesWithNaNAfterFirstValue = { 1.2f, Float.NaN, 3.7f, 27.0f, 42.0f, Float.NaN };
        assertEquals(1.2f, IEEE754rUtils.min(valuesWithNaNAfterFirstValue), FLOAT_TOLERANCE);
        assertEquals(42.0f, IEEE754rUtils.max(valuesWithNaNAfterFirstValue), FLOAT_TOLERANCE);

        final float[] valuesStartingWithNaN = { Float.NaN, 1.2f, Float.NaN, 3.7f, 27.0f, 42.0f, Float.NaN };
        assertEquals(1.2f, IEEE754rUtils.min(valuesStartingWithNaN), FLOAT_TOLERANCE);
        assertEquals(42.0f, IEEE754rUtils.max(valuesStartingWithNaN), FLOAT_TOLERANCE);
    }

}
