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
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.text.TextStringBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class LevenshteinDetailedDistanceTest {

    private static final LevenshteinDetailedDistance UNLIMITED_DISTANCE = LevenshteinDetailedDistance.getDefaultInstance();

    /**
     * Asserts all four fields of a {@link LevenshteinResults} in one call,
     * so each test case reads as a single line rather than four separate assertions.
     */
    private static void assertLevenshteinResults(
            final LevenshteinResults actual,
            final int expectedDistance,
            final int expectedInsert,
            final int expectedDelete,
            final int expectedSubstitute) {
        assertEquals(expectedDistance,   actual.getDistance());
        assertEquals(expectedInsert,     actual.getInsertCount());
        assertEquals(expectedDelete,     actual.getDeleteCount());
        assertEquals(expectedSubstitute, actual.getSubstituteCount());
    }

    // -----------------------------------------------------------------------
    // Constructor validation
    // -----------------------------------------------------------------------

    @Test
    void testConstructorWithNegativeThreshold() {
        assertThrows(IllegalArgumentException.class, () -> new LevenshteinDetailedDistance(-1));
    }

    @Test
    void testGetThreshold() {
        final LevenshteinDetailedDistance levenshteinDetailedDistance = new LevenshteinDetailedDistance(0);
        assertEquals(0, levenshteinDetailedDistance.getThreshold());
    }

    // -----------------------------------------------------------------------
    // Null-argument guard tests
    // -----------------------------------------------------------------------

    @Test
    void testApplyThrowsIllegalArgumentExceptionForNullSecondArg() {
        assertThrows(IllegalArgumentException.class, () -> {
            final LevenshteinDetailedDistance levenshteinDetailedDistance = new LevenshteinDetailedDistance(0);
            final CharSequence charSequence = new TextStringBuilder();
            levenshteinDetailedDistance.apply(charSequence, null);
        });
    }

    @Test
    void testApplyWithNullString() {
        assertThrows(IllegalArgumentException.class, () -> new LevenshteinDetailedDistance(0).apply((String) null, (String) null));
    }

    @Test
    void testGetLevenshteinDetailedDistance_NullString() {
        assertThrows(IllegalArgumentException.class, () -> UNLIMITED_DISTANCE.apply("a", null));
    }

    @Test
    void testGetLevenshteinDetailedDistance_NullStringInt() {
        assertThrows(IllegalArgumentException.class, () -> UNLIMITED_DISTANCE.apply(null, "a"));
    }

    @Test
    void testGetLevenshteinDetailedDistance_StringNull() {
        assertThrows(IllegalArgumentException.class, () -> UNLIMITED_DISTANCE.apply(null, "a"));
    }

    @Test
    void testGetLevenshteinDetailedDistance_StringNullInt() {
        assertThrows(IllegalArgumentException.class, () -> UNLIMITED_DISTANCE.apply("a", null));
    }

    @Test
    void testApplyWithNullSimilarityInput() {
        assertThrows(IllegalArgumentException.class,
                () -> new LevenshteinDetailedDistance(0).apply((SimilarityInput<Object>) null, (SimilarityInput<Object>) null));
        assertThrows(IllegalArgumentException.class,
                () -> new LevenshteinDetailedDistance(0).apply(new SimilarityCharacterInput("asdf"), (SimilarityCharacterInput) null));
        assertThrows(IllegalArgumentException.class,
                () -> new LevenshteinDetailedDistance(0).apply((SimilarityCharacterInput) null, new SimilarityCharacterInput("asdf")));
        assertThrows(IllegalArgumentException.class,
                () -> new LevenshteinDetailedDistance(null).apply(new SimilarityCharacterInput("asdf"), (SimilarityCharacterInput) null));
        assertThrows(IllegalArgumentException.class,
                () -> new LevenshteinDetailedDistance(null).apply((SimilarityCharacterInput) null, new SimilarityCharacterInput("asdf")));
    }

    // -----------------------------------------------------------------------
    // Unlimited distance (no threshold) — result counts
    // -----------------------------------------------------------------------

    @ParameterizedTest
    @MethodSource("org.apache.commons.text.similarity.SimilarityInputTest#similarityInputs()")
    void testGetLevenshteinDetailedDistance_StringString(final Class<?> cls) {
        // Both strings empty → no edits needed
        assertLevenshteinResults(
                UNLIMITED_DISTANCE.apply(SimilarityInputTest.build(cls, ""), SimilarityInputTest.build(cls, "")),
                0, 0, 0, 0);

        // Empty left, one-char right → one insertion
        assertLevenshteinResults(
                UNLIMITED_DISTANCE.apply(SimilarityInputTest.build(cls, ""), SimilarityInputTest.build(cls, "a")),
                1, 1, 0, 0);

        // Non-empty left, empty right → all deletions
        assertLevenshteinResults(
                UNLIMITED_DISTANCE.apply(SimilarityInputTest.build(cls, "aaapppp"), SimilarityInputTest.build(cls, "")),
                7, 0, 7, 0);

        // "frog" → "fog": one character deleted
        assertLevenshteinResults(
                UNLIMITED_DISTANCE.apply(SimilarityInputTest.build(cls, "frog"), SimilarityInputTest.build(cls, "fog")),
                1, 0, 1, 0);

        // "fly" → "ant": all three characters substituted
        assertLevenshteinResults(
                UNLIMITED_DISTANCE.apply(SimilarityInputTest.build(cls, "fly"), SimilarityInputTest.build(cls, "ant")),
                3, 0, 0, 3);

        // "elephant" → "hippo": deletions + substitutions (left longer than right)
        assertLevenshteinResults(
                UNLIMITED_DISTANCE.apply(SimilarityInputTest.build(cls, "elephant"), SimilarityInputTest.build(cls, "hippo")),
                7, 0, 3, 4);

        // "hippo" → "elephant": insertions + substitutions (right longer than left)
        assertLevenshteinResults(
                UNLIMITED_DISTANCE.apply(SimilarityInputTest.build(cls, "hippo"), SimilarityInputTest.build(cls, "elephant")),
                7, 3, 0, 4);

        // "hippo" → "zzzzzzzz": insertions + substitutions
        assertLevenshteinResults(
                UNLIMITED_DISTANCE.apply(SimilarityInputTest.build(cls, "hippo"), SimilarityInputTest.build(cls, "zzzzzzzz")),
                8, 3, 0, 5);

        // "zzzzzzzz" → "hippo": deletions + substitutions
        assertLevenshteinResults(
                UNLIMITED_DISTANCE.apply(SimilarityInputTest.build(cls, "zzzzzzzz"), SimilarityInputTest.build(cls, "hippo")),
                8, 0, 3, 5);

        // "hello" → "hallo": single substitution (e → a)
        assertLevenshteinResults(
                UNLIMITED_DISTANCE.apply(SimilarityInputTest.build(cls, "hello"), SimilarityInputTest.build(cls, "hallo")),
                1, 0, 0, 1);
    }

    // -----------------------------------------------------------------------
    // Limited distance (with threshold) — result counts and threshold behaviour
    // -----------------------------------------------------------------------

    @Test
    void testGetLevenshteinDetailedDistance_StringStringInt() {
        // Threshold 0, both empty → distance is 0 (within threshold)
        assertLevenshteinResults(new LevenshteinDetailedDistance(0).apply("", ""), 0, 0, 0, 0);

        // Threshold 8 > actual distance 7 → result returned
        assertLevenshteinResults(new LevenshteinDetailedDistance(8).apply("aaapppp", ""), 7, 0, 7, 0);

        // Threshold 7 == actual distance 7 → result returned (boundary)
        assertLevenshteinResults(new LevenshteinDetailedDistance(7).apply("aaapppp", ""), 7, 0, 7, 0);

        // Threshold 6 < actual distance 7 → exceeds threshold, returns -1
        assertLevenshteinResults(new LevenshteinDetailedDistance(6).apply("aaapppp", ""), -1, 0, 0, 0);

        // Threshold 0, substitution needed → exceeds threshold, returns -1
        assertLevenshteinResults(new LevenshteinDetailedDistance(0).apply("b", "a"), -1, 0, 0, 0);
        assertLevenshteinResults(new LevenshteinDetailedDistance(0).apply("a", "b"), -1, 0, 0, 0);

        // Threshold 0, identical strings → distance is 0 (within threshold)
        assertLevenshteinResults(new LevenshteinDetailedDistance(0).apply("aa", "aa"), 0, 0, 0, 0);

        // Threshold 2 > actual distance 0 for identical strings → result returned
        assertLevenshteinResults(new LevenshteinDetailedDistance(2).apply("aa", "aa"), 0, 0, 0, 0);

        // Threshold 2 < actual distance 3 → exceeds threshold, returns -1
        assertLevenshteinResults(new LevenshteinDetailedDistance(2).apply("aaa", "bbb"), -1, 0, 0, 0);

        // Threshold 3 == actual distance 3 → result returned (boundary)
        assertLevenshteinResults(new LevenshteinDetailedDistance(3).apply("aaa", "bbb"), 3, 0, 0, 3);

        // Threshold 10 > actual distance 6 → result returned
        assertLevenshteinResults(new LevenshteinDetailedDistance(10).apply("aaaaaa", "b"), 6, 0, 5, 1);

        // Threshold 8 > actual distance 7 → result returned
        assertLevenshteinResults(new LevenshteinDetailedDistance(8).apply("aaapppp", "b"), 7, 0, 6, 1);

        // Threshold 4 > actual distance 3 → result returned
        assertLevenshteinResults(new LevenshteinDetailedDistance(4).apply("a", "bbb"), 3, 2, 0, 1);

        // Threshold 7 == actual distance 7 → result returned (boundary)
        assertLevenshteinResults(new LevenshteinDetailedDistance(7).apply("aaapppp", "b"), 7, 0, 6, 1);

        // Threshold 3 == actual distance 3 → result returned (boundary)
        assertLevenshteinResults(new LevenshteinDetailedDistance(3).apply("a", "bbb"), 3, 2, 0, 1);

        // Threshold 2 < actual distance 3 → exceeds threshold, returns -1
        assertLevenshteinResults(new LevenshteinDetailedDistance(2).apply("a", "bbb"), -1, 0, 0, 0);

        // Threshold 2 < actual distance 3 (reversed) → exceeds threshold, returns -1
        assertLevenshteinResults(new LevenshteinDetailedDistance(2).apply("bbb", "a"), -1, 0, 0, 0);

        // Threshold 6 < actual distance 7 → exceeds threshold, returns -1
        assertLevenshteinResults(new LevenshteinDetailedDistance(6).apply("aaapppp", "b"), -1, 0, 0, 0);

        // Threshold 1 < actual distance 3 → exceeds threshold, returns -1
        assertLevenshteinResults(new LevenshteinDetailedDistance(1).apply("a", "bbb"), -1, 0, 0, 0);

        // Threshold 1 < actual distance 3 (reversed) → exceeds threshold, returns -1
        assertLevenshteinResults(new LevenshteinDetailedDistance(1).apply("bbb", "a"), -1, 0, 0, 0);

        // Threshold 1 < actual distance 2 (length difference alone exceeds threshold)
        assertLevenshteinResults(new LevenshteinDetailedDistance(1).apply("12345", "1234567"), -1, 0, 0, 0);
        assertLevenshteinResults(new LevenshteinDetailedDistance(1).apply("1234567", "12345"), -1, 0, 0, 0);

        // Threshold 1 == actual distance 1: "frog" → "fog" (one deletion)
        assertLevenshteinResults(new LevenshteinDetailedDistance(1).apply("frog", "fog"), 1, 0, 1, 0);

        // Threshold 3 == actual distance 3: "fly" → "ant" (all substitutions)
        assertLevenshteinResults(new LevenshteinDetailedDistance(3).apply("fly", "ant"), 3, 0, 0, 3);

        // Threshold 7 == actual distance 7: "elephant" → "hippo"
        assertLevenshteinResults(new LevenshteinDetailedDistance(7).apply("elephant", "hippo"), 7, 0, 3, 4);

        // Threshold 6 < actual distance 7: "elephant" → "hippo" → exceeds threshold
        assertLevenshteinResults(new LevenshteinDetailedDistance(6).apply("elephant", "hippo"), -1, 0, 0, 0);

        // Threshold 7 == actual distance 7: "hippo" → "elephant"
        assertLevenshteinResults(new LevenshteinDetailedDistance(7).apply("hippo", "elephant"), 7, 3, 0, 4);

        // Threshold 7 == actual distance 7: "hippo" → "elephant" (duplicate case from original)
        assertLevenshteinResults(new LevenshteinDetailedDistance(7).apply("hippo", "elephant"), 7, 3, 0, 4);

        // Threshold 6 < actual distance 7: "hippo" → "elephant" → exceeds threshold
        assertLevenshteinResults(new LevenshteinDetailedDistance(6).apply("hippo", "elephant"), -1, 0, 0, 0);

        // Threshold 8 == actual distance 8: "hippo" → "zzzzzzzz"
        assertLevenshteinResults(new LevenshteinDetailedDistance(8).apply("hippo", "zzzzzzzz"), 8, 3, 0, 5);

        // Threshold 8 == actual distance 8: "zzzzzzzz" → "hippo"
        assertLevenshteinResults(new LevenshteinDetailedDistance(8).apply("zzzzzzzz", "hippo"), 8, 0, 3, 5);

        // Threshold 1 == actual distance 1: "hello" → "hallo" (one substitution)
        assertLevenshteinResults(new LevenshteinDetailedDistance(1).apply("hello", "hallo"), 1, 0, 0, 1);

        // Integer.MAX_VALUE threshold (effectively unlimited): same results as UNLIMITED_DISTANCE
        assertLevenshteinResults(new LevenshteinDetailedDistance(Integer.MAX_VALUE).apply("frog", "fog"),      1, 0, 1, 0);
        assertLevenshteinResults(new LevenshteinDetailedDistance(Integer.MAX_VALUE).apply("fly", "ant"),       3, 0, 0, 3);
        assertLevenshteinResults(new LevenshteinDetailedDistance(Integer.MAX_VALUE).apply("elephant", "hippo"),7, 0, 3, 4);
        assertLevenshteinResults(new LevenshteinDetailedDistance(Integer.MAX_VALUE).apply("hippo", "elephant"),7, 3, 0, 4);
        assertLevenshteinResults(new LevenshteinDetailedDistance(Integer.MAX_VALUE).apply("hippo", "zzzzzzzz"),8, 3, 0, 5);
        assertLevenshteinResults(new LevenshteinDetailedDistance(Integer.MAX_VALUE).apply("zzzzzzzz", "hippo"),8, 0, 3, 5);
        assertLevenshteinResults(new LevenshteinDetailedDistance(Integer.MAX_VALUE).apply("hello", "hallo"),   1, 0, 0, 1);
    }

    // -----------------------------------------------------------------------
    // Threshold-exceeded behaviour with long strings
    // -----------------------------------------------------------------------

    @ParameterizedTest
    @MethodSource("org.apache.commons.text.similarity.SimilarityInputTest#similarityInputs()")
    void testThresholdZeroWithLongInputExceedsThreshold(final Class<?> cls) {
        final LevenshteinDetailedDistance levenshteinDetailedDistance = new LevenshteinDetailedDistance(0);
        final LevenshteinResults levenshteinResults = levenshteinDetailedDistance.apply("", "Distance: 38, Insert: 0, Delete: 0, Substitute: 0");
        assertEquals(0, levenshteinResults.getSubstituteCount());
        assertEquals(0, levenshteinResults.getDeleteCount());
        assertEquals(0, levenshteinResults.getInsertCount());
        assertEquals(-1, levenshteinResults.getDistance());
        // SimilarityInput variant must return the same result as the String variant
        assertEquals(levenshteinResults, levenshteinDetailedDistance.apply(SimilarityInputTest.build(cls, ""),
                SimilarityInputTest.build(cls, "Distance: 38, Insert: 0, Delete: 0, Substitute: 0")));
    }

    // -----------------------------------------------------------------------
    // SimilarityInput equivalence — String and SimilarityInput variants agree
    // -----------------------------------------------------------------------

    @ParameterizedTest
    @MethodSource("org.apache.commons.text.similarity.SimilarityInputTest#similarityInputs()")
    void testEquals(final Class<?> cls) {
        final LevenshteinDetailedDistance classBeingTested = LevenshteinDetailedDistance.getDefaultInstance();

        // "hello" → "hallo": one substitution
        LevenshteinResults actualResult = classBeingTested.apply(SimilarityInputTest.build(cls, "hello"), SimilarityInputTest.build(cls, "hallo"));
        LevenshteinResults expectedResult = new LevenshteinResults(1, 0, 0, 1);
        assertEquals(expectedResult, actualResult);

        // String and SimilarityInput variants must produce identical results
        assertEquals(classBeingTested.apply("zzzzzzzz", "hippo"),
                classBeingTested.apply(SimilarityInputTest.build(cls, "zzzzzzzz"), SimilarityInputTest.build(cls, "hippo")));
        actualResult = classBeingTested.apply(SimilarityInputTest.build(cls, "zzzzzzzz"), SimilarityInputTest.build(cls, "hippo"));
        expectedResult = new LevenshteinResults(8, 0, 3, 5);
        assertEquals(expectedResult, actualResult);
        assertEquals(actualResult, actualResult); // intentionally added

        // Both strings empty
        actualResult = classBeingTested.apply(SimilarityInputTest.build(cls, ""), SimilarityInputTest.build(cls, ""));
        expectedResult = new LevenshteinResults(0, 0, 0, 0);
        assertEquals(expectedResult, actualResult);
    }

    @ParameterizedTest
    @MethodSource("org.apache.commons.text.similarity.SimilarityInputTest#similarityInputs()")
    void testGetDefaultInstanceOne(final Class<?> cls) {
        final LevenshteinDetailedDistance levenshteinDetailedDistance = LevenshteinDetailedDistance.getDefaultInstance();
        final LevenshteinResults levenshteinResults = levenshteinDetailedDistance.apply(
                SimilarityInputTest.build(cls, "Distance: -2147483643, Insert: 0, Delete: 0, Substitute: 0"),
                SimilarityInputTest.build(cls, "Distance: 0, Insert: 2147483536, Delete: 0, Substitute: 0"));

        assertEquals(21, levenshteinResults.getDistance());
    }

    @ParameterizedTest
    @MethodSource("org.apache.commons.text.similarity.SimilarityInputTest#similarityInputs()")
    void testGetDefaultInstanceTwo(final Class<?> cls) {
        final LevenshteinDetailedDistance levenshteinDetailedDistance = LevenshteinDetailedDistance.getDefaultInstance();
        final LevenshteinResults levenshteinResults = levenshteinDetailedDistance.apply("Distance: 2147483647, Insert: 0, Delete: 0, Substitute: 0",
                "Distance: 0, Insert: 2147483647, Delete: 0, Substitute: 0");
        assertEquals(20, levenshteinResults.getDistance());
        // SimilarityInput variant must return the same result as the String variant
        assertEquals(levenshteinResults,
                levenshteinDetailedDistance.apply(SimilarityInputTest.build(cls, "Distance: 2147483647, Insert: 0, Delete: 0, Substitute: 0"),
                        SimilarityInputTest.build(cls, "Distance: 0, Insert: 2147483647, Delete: 0, Substitute: 0")));
    }

    // -----------------------------------------------------------------------
    // LevenshteinResults object contract (hashCode / toString)
    // -----------------------------------------------------------------------

    @Test
    void testHashCode() {
        final LevenshteinDetailedDistance classBeingTested = LevenshteinDetailedDistance.getDefaultInstance();

        // "aaapppp" → "": all deletions
        LevenshteinResults actualResult = classBeingTested.apply("aaapppp", "");
        LevenshteinResults expectedResult = new LevenshteinResults(7, 0, 7, 0);
        assertEquals(expectedResult.hashCode(), actualResult.hashCode());

        // "frog" → "fog": one deletion
        actualResult = classBeingTested.apply("frog", "fog");
        expectedResult = new LevenshteinResults(1, 0, 1, 0);
        assertEquals(expectedResult.hashCode(), actualResult.hashCode());

        // "elephant" → "hippo": mixed deletions and substitutions
        actualResult = classBeingTested.apply("elephant", "hippo");
        expectedResult = new LevenshteinResults(7, 0, 3, 4);
        assertEquals(expectedResult.hashCode(), actualResult.hashCode());
    }

    @Test
    void testToString() {
        final LevenshteinDetailedDistance classBeingTested = LevenshteinDetailedDistance.getDefaultInstance();

        // "fly" → "ant": all substitutions
        LevenshteinResults actualResult = classBeingTested.apply("fly", "ant");
        LevenshteinResults expectedResult = new LevenshteinResults(3, 0, 0, 3);
        assertEquals(expectedResult.toString(), actualResult.toString());

        // "hippo" → "elephant": insertions + substitutions
        actualResult = classBeingTested.apply("hippo", "elephant");
        expectedResult = new LevenshteinResults(7, 3, 0, 4);
        assertEquals(expectedResult.toString(), actualResult.toString());

        // "" → "a": single insertion
        actualResult = classBeingTested.apply("", "a");
        expectedResult = new LevenshteinResults(1, 1, 0, 0);
        assertEquals(expectedResult.toString(), actualResult.toString());
    }
}
