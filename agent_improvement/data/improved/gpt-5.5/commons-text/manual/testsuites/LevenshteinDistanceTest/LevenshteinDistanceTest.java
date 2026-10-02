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
package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests {@link LevenshteinDistance}.
 */
class LevenshteinDistanceTest {

    private static final LevenshteinDistance UNLIMITED_DISTANCE = LevenshteinDistance.getDefaultInstance();

    private static void assertLimitedDistance(final int expected, final int threshold, final Class<?> cls, final String left,
            final String right) {
        assertEquals(expected, new LevenshteinDistance(threshold).apply(SimilarityInputTest.build(cls, left),
                SimilarityInputTest.build(cls, right)));
    }

    private static void assertUnlimitedDistance(final int expected, final Class<?> cls, final String left, final String right) {
        assertEquals(expected, UNLIMITED_DISTANCE.apply(SimilarityInputTest.build(cls, left), SimilarityInputTest.build(cls, right)));
    }

    @Test
    void testApplyThrowsIllegalArgumentExceptionSimilarityInput() {
        assertThrows(IllegalArgumentException.class, () -> new LevenshteinDistance(0).apply((SimilarityInput<Object>) null,
                (SimilarityInput<Object>) null));
        assertThrows(IllegalArgumentException.class, () -> new LevenshteinDistance(0).apply(new SimilarityCharacterInput("asdf"),
                (SimilarityCharacterInput) null));
        assertThrows(IllegalArgumentException.class, () -> new LevenshteinDistance(0).apply((SimilarityCharacterInput) null,
                new SimilarityCharacterInput("asdf")));
    }

    @Test
    void testApplyThrowsIllegalArgumentExceptionString() {
        assertThrows(IllegalArgumentException.class, () -> new LevenshteinDistance(0).apply((String) null, (String) null));
    }

    @Test
    void testConstructorWithNegativeThreshold() {
        assertThrows(IllegalArgumentException.class, () -> new LevenshteinDistance(-1));
    }

    @ParameterizedTest
    @MethodSource("org.apache.commons.text.similarity.SimilarityInputTest#similarityInputs()")
    void testGetLevenshteinDistance(final Class<?> cls) {
        assertUnlimitedDistance(0, cls, "", "");
        assertUnlimitedDistance(1, cls, "", "a");
        assertUnlimitedDistance(7, cls, "aaapppp", "");
        assertUnlimitedDistance(1, cls, "frog", "fog");
        assertUnlimitedDistance(3, cls, "fly", "ant");
        assertUnlimitedDistance(7, cls, "elephant", "hippo");
        assertUnlimitedDistance(7, cls, "hippo", "elephant");
        assertUnlimitedDistance(8, cls, "hippo", "zzzzzzzz");
        assertUnlimitedDistance(8, cls, "zzzzzzzz", "hippo");
        assertUnlimitedDistance(1, cls, "hello", "hallo");
    }

    @Test
    void testGetLevenshteinDistance_EmptyStringString() {
        assertEquals(-1, new LevenshteinDistance(0).apply(new SimilarityCharacterInput(""), new SimilarityCharacterInput("asdf")));
    }

    @ParameterizedTest
    @MethodSource("org.apache.commons.text.similarity.SimilarityInputTest#similarityInputs()")
    void testGetLevenshteinDistance_NullString(final Class<?> cls) {
        assertThrows(IllegalArgumentException.class, () -> UNLIMITED_DISTANCE.apply(SimilarityInputTest.build(cls, "a"),
                SimilarityInputTest.build(cls, null)));
    }

    @ParameterizedTest
    @MethodSource("org.apache.commons.text.similarity.SimilarityInputTest#similarityInputs()")
    void testGetLevenshteinDistance_NullStringInt(final Class<?> cls) {
        assertThrows(IllegalArgumentException.class, () -> UNLIMITED_DISTANCE.apply(SimilarityInputTest.build(cls, null),
                SimilarityInputTest.build(cls, "a")));
    }

    @ParameterizedTest
    @MethodSource("org.apache.commons.text.similarity.SimilarityInputTest#similarityInputs()")
    void testGetLevenshteinDistance_StringNull(final Class<?> cls) {
        assertThrows(IllegalArgumentException.class, () -> UNLIMITED_DISTANCE.apply(SimilarityInputTest.build(cls, null),
                SimilarityInputTest.build(cls, "a")));
    }

    @ParameterizedTest
    @MethodSource("org.apache.commons.text.similarity.SimilarityInputTest#similarityInputs()")
    void testGetLevenshteinDistance_StringNullInt(final Class<?> cls) {
        assertThrows(IllegalArgumentException.class, () -> UNLIMITED_DISTANCE.apply(SimilarityInputTest.build(cls, "a"),
                SimilarityInputTest.build(cls, null)));
    }

    @ParameterizedTest
    @MethodSource("org.apache.commons.text.similarity.SimilarityInputTest#similarityInputs()")
    void testGetLevenshteinDistance_StringString(final Class<?> cls) {
        assertUnlimitedDistance(0, cls, "", "");
        assertUnlimitedDistance(1, cls, "", "a");
        assertUnlimitedDistance(7, cls, "aaapppp", "");
        assertUnlimitedDistance(1, cls, "frog", "fog");
        assertUnlimitedDistance(3, cls, "fly", "ant");
        assertUnlimitedDistance(7, cls, "elephant", "hippo");
        assertUnlimitedDistance(7, cls, "hippo", "elephant");
        assertUnlimitedDistance(8, cls, "hippo", "zzzzzzzz");
        assertUnlimitedDistance(8, cls, "zzzzzzzz", "hippo");
        assertUnlimitedDistance(1, cls, "hello", "hallo");
    }

    @ParameterizedTest
    @MethodSource("org.apache.commons.text.similarity.SimilarityInputTest#similarityInputs()")
    void testGetLevenshteinDistance_StringStringInt(final Class<?> cls) {
        assertEmptyStringThresholdCases(cls);
        assertZeroThresholdForUnequalStrings(cls);
        assertEqualStringThresholdCases(cls);
        assertSameLengthThresholdCases(cls);
        assertLargeStripeThresholdCase(cls);
        assertDistanceBelowThresholdCases(cls);
        assertDistanceAtThresholdCases(cls);
        assertDistanceAboveThresholdCases(cls);
        assertStripeOffArrayNotSimilarCases(cls);
        assertStripeOffArraySimilarCases(cls);
        assertHistoricalLimitedDistanceCases(cls);
        assertMaxThresholdMatchesUnlimitedDistanceCases(cls);
        assertLimitedDistance(-1, 1, cls, "abc", "acb");
    }

    private static void assertEmptyStringThresholdCases(final Class<?> cls) {
        assertLimitedDistance(0, 0, cls, "", "");
        assertLimitedDistance(7, 8, cls, "aaapppp", "");
        assertLimitedDistance(7, 7, cls, "aaapppp", "");
        assertLimitedDistance(-1, 6, cls, "aaapppp", "");
    }

    private static void assertZeroThresholdForUnequalStrings(final Class<?> cls) {
        assertLimitedDistance(-1, 0, cls, "b", "a");
        assertLimitedDistance(-1, 0, cls, "a", "b");
    }

    private static void assertEqualStringThresholdCases(final Class<?> cls) {
        assertLimitedDistance(0, 0, cls, "aa", "aa");
        assertLimitedDistance(0, 2, cls, "aa", "aa");
    }

    private static void assertSameLengthThresholdCases(final Class<?> cls) {
        assertLimitedDistance(-1, 2, cls, "aaa", "bbb");
        assertLimitedDistance(3, 3, cls, "aaa", "bbb");
    }

    private static void assertLargeStripeThresholdCase(final Class<?> cls) {
        assertLimitedDistance(6, 10, cls, "aaaaaa", "b");
    }

    private static void assertDistanceBelowThresholdCases(final Class<?> cls) {
        assertLimitedDistance(7, 8, cls, "aaapppp", "b");
        assertLimitedDistance(3, 4, cls, "a", "bbb");
    }

    private static void assertDistanceAtThresholdCases(final Class<?> cls) {
        assertLimitedDistance(7, 7, cls, "aaapppp", "b");
        assertLimitedDistance(3, 3, cls, "a", "bbb");
    }

    private static void assertDistanceAboveThresholdCases(final Class<?> cls) {
        assertLimitedDistance(-1, 2, cls, "a", "bbb");
        assertLimitedDistance(-1, 2, cls, "bbb", "a");
        assertLimitedDistance(-1, 6, cls, "aaapppp", "b");
    }

    private static void assertStripeOffArrayNotSimilarCases(final Class<?> cls) {
        assertLimitedDistance(-1, 1, cls, "a", "bbb");
        assertLimitedDistance(-1, 1, cls, "bbb", "a");
    }

    private static void assertStripeOffArraySimilarCases(final Class<?> cls) {
        assertLimitedDistance(-1, 1, cls, "12345", "1234567");
        assertLimitedDistance(-1, 1, cls, "1234567", "12345");
    }

    private static void assertHistoricalLimitedDistanceCases(final Class<?> cls) {
        assertLimitedDistance(1, 1, cls, "frog", "fog");
        assertLimitedDistance(3, 3, cls, "fly", "ant");
        assertLimitedDistance(7, 7, cls, "elephant", "hippo");
        assertLimitedDistance(-1, 6, cls, "elephant", "hippo");
        assertLimitedDistance(7, 7, cls, "hippo", "elephant");
        assertLimitedDistance(-1, 6, cls, "hippo", "elephant");
        assertLimitedDistance(8, 8, cls, "hippo", "zzzzzzzz");
        assertLimitedDistance(8, 8, cls, "zzzzzzzz", "hippo");
        assertLimitedDistance(1, 1, cls, "hello", "hallo");
    }

    private static void assertMaxThresholdMatchesUnlimitedDistanceCases(final Class<?> cls) {
        assertLimitedDistance(1, Integer.MAX_VALUE, cls, "frog", "fog");
        assertLimitedDistance(3, Integer.MAX_VALUE, cls, "fly", "ant");
        assertLimitedDistance(7, Integer.MAX_VALUE, cls, "elephant", "hippo");
        assertLimitedDistance(7, Integer.MAX_VALUE, cls, "hippo", "elephant");
        assertLimitedDistance(8, Integer.MAX_VALUE, cls, "hippo", "zzzzzzzz");
        assertLimitedDistance(8, Integer.MAX_VALUE, cls, "zzzzzzzz", "hippo");
        assertLimitedDistance(1, Integer.MAX_VALUE, cls, "hello", "hallo");
    }

    @Test
    void testGetThresholdDirectlyAfterObjectInstantiation() {
        assertNull(LevenshteinDistance.getDefaultInstance().getThreshold());
    }

}
