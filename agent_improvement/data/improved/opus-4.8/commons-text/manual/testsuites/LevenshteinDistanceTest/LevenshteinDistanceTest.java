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

    /** Reference to the {@code similarityInputs()} factory used by every {@code @MethodSource}. */
    private static final String SIMILARITY_INPUTS = "org.apache.commons.text.similarity.SimilarityInputTest#similarityInputs()";

    /** Default instance: no threshold, so distances are computed exactly (never capped to -1). */
    private static final LevenshteinDistance UNLIMITED_DISTANCE = LevenshteinDistance.getDefaultInstance();

    /**
     * Asserts that {@code distance.apply(left, right)} equals {@code expected}, where {@code left} and
     * {@code right} are wrapped in the {@link SimilarityInput} implementation identified by {@code cls}.
     */
    private static void assertDistance(final int expected, final LevenshteinDistance distance, final Class<?> cls,
            final String left, final String right) {
        assertEquals(expected, distance.apply(SimilarityInputTest.build(cls, left), SimilarityInputTest.build(cls, right)));
    }

    /**
     * Asserts that the {@link #UNLIMITED_DISTANCE} rejects the given {@code left}/{@code right} pair (wrapped in the
     * {@link SimilarityInput} implementation identified by {@code cls}) with an {@link IllegalArgumentException}.
     */
    private static void assertRejected(final Class<?> cls, final String left, final String right) {
        assertThrows(IllegalArgumentException.class,
                () -> UNLIMITED_DISTANCE.apply(SimilarityInputTest.build(cls, left), SimilarityInputTest.build(cls, right)));
    }

    /**
     * The canonical set of exact-distance examples (taken from the {@code unlimitedCompare} Javadoc), shared by every
     * test that exercises the unbounded algorithm so the two stay in lock-step.
     */
    private static void assertUnlimitedExamples(final Class<?> cls) {
        assertDistance(0, UNLIMITED_DISTANCE, cls, "", "");
        assertDistance(1, UNLIMITED_DISTANCE, cls, "", "a");
        assertDistance(7, UNLIMITED_DISTANCE, cls, "aaapppp", "");
        assertDistance(1, UNLIMITED_DISTANCE, cls, "frog", "fog");
        assertDistance(3, UNLIMITED_DISTANCE, cls, "fly", "ant");
        assertDistance(7, UNLIMITED_DISTANCE, cls, "elephant", "hippo");
        assertDistance(7, UNLIMITED_DISTANCE, cls, "hippo", "elephant");
        assertDistance(8, UNLIMITED_DISTANCE, cls, "hippo", "zzzzzzzz");
        assertDistance(8, UNLIMITED_DISTANCE, cls, "zzzzzzzz", "hippo");
        assertDistance(1, UNLIMITED_DISTANCE, cls, "hello", "hallo");
    }

    @Test
    void testApplyThrowsIllegalArgumentExceptionSimilarityInput() {
        // A null input (on either side, or both) is always illegal.
        assertThrows(IllegalArgumentException.class,
                () -> new LevenshteinDistance(0).apply((SimilarityInput<Object>) null, (SimilarityInput<Object>) null));
        assertThrows(IllegalArgumentException.class,
                () -> new LevenshteinDistance(0).apply(new SimilarityCharacterInput("asdf"), (SimilarityCharacterInput) null));
        assertThrows(IllegalArgumentException.class,
                () -> new LevenshteinDistance(0).apply((SimilarityCharacterInput) null, new SimilarityCharacterInput("asdf")));
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
    @MethodSource(SIMILARITY_INPUTS)
    void testGetLevenshteinDistance(final Class<?> cls) {
        assertUnlimitedExamples(cls);
    }

    @Test
    void testGetLevenshteinDistance_EmptyStringString() {
        // Distance from "" to "asdf" is 4, which exceeds the threshold of 0, so the result is capped to -1.
        assertEquals(-1, new LevenshteinDistance(0).apply(new SimilarityCharacterInput(""), new SimilarityCharacterInput("asdf")));
    }

    @ParameterizedTest
    @MethodSource(SIMILARITY_INPUTS)
    void testGetLevenshteinDistance_NullString(final Class<?> cls) {
        // right input is null -> rejected.
        assertRejected(cls, "a", null);
    }

    @ParameterizedTest
    @MethodSource(SIMILARITY_INPUTS)
    void testGetLevenshteinDistance_NullStringInt(final Class<?> cls) {
        // left input is null -> rejected.
        assertRejected(cls, null, "a");
    }

    @ParameterizedTest
    @MethodSource(SIMILARITY_INPUTS)
    void testGetLevenshteinDistance_StringNull(final Class<?> cls) {
        // left input is null -> rejected.
        assertRejected(cls, null, "a");
    }

    @ParameterizedTest
    @MethodSource(SIMILARITY_INPUTS)
    void testGetLevenshteinDistance_StringNullInt(final Class<?> cls) {
        // right input is null -> rejected.
        assertRejected(cls, "a", null);
    }

    @ParameterizedTest
    @MethodSource(SIMILARITY_INPUTS)
    void testGetLevenshteinDistance_StringString(final Class<?> cls) {
        assertUnlimitedExamples(cls);
    }

    @ParameterizedTest
    @MethodSource(SIMILARITY_INPUTS)
    void testGetLevenshteinDistance_StringStringInt(final Class<?> cls) {
        // empty strings
        assertDistance(0, new LevenshteinDistance(0), cls, "", "");
        assertDistance(7, new LevenshteinDistance(8), cls, "aaapppp", "");
        assertDistance(7, new LevenshteinDistance(7), cls, "aaapppp", "");
        assertDistance(-1, new LevenshteinDistance(6), cls, "aaapppp", "");

        // unequal strings, zero threshold
        assertDistance(-1, new LevenshteinDistance(0), cls, "b", "a");
        assertDistance(-1, new LevenshteinDistance(0), cls, "a", "b");

        // equal strings
        assertDistance(0, new LevenshteinDistance(0), cls, "aa", "aa");
        assertDistance(0, new LevenshteinDistance(2), cls, "aa", "aa");

        // same length
        assertDistance(-1, new LevenshteinDistance(2), cls, "aaa", "bbb");
        assertDistance(3, new LevenshteinDistance(3), cls, "aaa", "bbb");

        // big stripe
        assertDistance(6, new LevenshteinDistance(10), cls, "aaaaaa", "b");

        // distance less than threshold
        assertDistance(7, new LevenshteinDistance(8), cls, "aaapppp", "b");
        assertDistance(3, new LevenshteinDistance(4), cls, "a", "bbb");

        // distance equal to threshold
        assertDistance(7, new LevenshteinDistance(7), cls, "aaapppp", "b");
        assertDistance(3, new LevenshteinDistance(3), cls, "a", "bbb");

        // distance greater than threshold
        assertDistance(-1, new LevenshteinDistance(2), cls, "a", "bbb");
        assertDistance(-1, new LevenshteinDistance(2), cls, "bbb", "a");
        assertDistance(-1, new LevenshteinDistance(6), cls, "aaapppp", "b");

        // stripe runs off array, strings not similar
        assertDistance(-1, new LevenshteinDistance(1), cls, "a", "bbb");
        assertDistance(-1, new LevenshteinDistance(1), cls, "bbb", "a");

        // stripe runs off array, strings are similar
        assertDistance(-1, new LevenshteinDistance(1), cls, "12345", "1234567");
        assertDistance(-1, new LevenshteinDistance(1), cls, "1234567", "12345");

        // old getLevenshteinDistance test cases
        assertDistance(1, new LevenshteinDistance(1), cls, "frog", "fog");
        assertDistance(3, new LevenshteinDistance(3), cls, "fly", "ant");
        assertDistance(7, new LevenshteinDistance(7), cls, "elephant", "hippo");
        assertDistance(-1, new LevenshteinDistance(6), cls, "elephant", "hippo");
        assertDistance(7, new LevenshteinDistance(7), cls, "hippo", "elephant");
        assertDistance(-1, new LevenshteinDistance(6), cls, "hippo", "elephant");
        assertDistance(8, new LevenshteinDistance(8), cls, "hippo", "zzzzzzzz");
        assertDistance(8, new LevenshteinDistance(8), cls, "zzzzzzzz", "hippo");
        assertDistance(1, new LevenshteinDistance(1), cls, "hello", "hallo");

        // a threshold of Integer.MAX_VALUE never caps, so it reproduces the exact distance
        assertDistance(1, new LevenshteinDistance(Integer.MAX_VALUE), cls, "frog", "fog");
        assertDistance(3, new LevenshteinDistance(Integer.MAX_VALUE), cls, "fly", "ant");
        assertDistance(7, new LevenshteinDistance(Integer.MAX_VALUE), cls, "elephant", "hippo");
        assertDistance(7, new LevenshteinDistance(Integer.MAX_VALUE), cls, "hippo", "elephant");
        assertDistance(8, new LevenshteinDistance(Integer.MAX_VALUE), cls, "hippo", "zzzzzzzz");
        assertDistance(8, new LevenshteinDistance(Integer.MAX_VALUE), cls, "zzzzzzzz", "hippo");
        assertDistance(1, new LevenshteinDistance(Integer.MAX_VALUE), cls, "hello", "hallo");
        assertDistance(-1, new LevenshteinDistance(1), cls, "abc", "acb");
    }

    @Test
    void testGetThresholdDirectlyAfterObjectInstantiation() {
        assertNull(LevenshteinDistance.getDefaultInstance().getThreshold());
    }

}
