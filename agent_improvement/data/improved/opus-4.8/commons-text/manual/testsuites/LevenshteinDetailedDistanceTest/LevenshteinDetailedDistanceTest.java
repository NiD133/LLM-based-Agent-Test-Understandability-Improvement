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

    /** A distance with no threshold; it always computes the exact edit distance. */
    private static final LevenshteinDetailedDistance UNLIMITED_DISTANCE = LevenshteinDetailedDistance.getDefaultInstance();

    /**
     * Asserts that a {@link LevenshteinResults} carries exactly the expected distance and the
     * expected breakdown of insert, delete and substitute operations.
     *
     * @param actual         the results produced by the algorithm.
     * @param distance       the expected total distance ({@code -1} when a threshold is exceeded).
     * @param insertCount    the expected number of insertions.
     * @param deleteCount    the expected number of deletions.
     * @param substituteCount the expected number of substitutions.
     */
    private static void assertResults(final LevenshteinResults actual, final int distance, final int insertCount,
            final int deleteCount, final int substituteCount) {
        assertEquals(distance, actual.getDistance());
        assertEquals(insertCount, actual.getInsertCount());
        assertEquals(deleteCount, actual.getDeleteCount());
        assertEquals(substituteCount, actual.getSubstituteCount());
    }

    @Test
    void testApplyThrowsIllegalArgumentExceptionAndCreatesLevenshteinDetailedDistanceTakingInteger() {
        // A null input must be rejected even when the other input is a valid (empty) CharSequence.
        assertThrows(IllegalArgumentException.class, () -> {
            final LevenshteinDetailedDistance distance = new LevenshteinDetailedDistance(0);
            final CharSequence emptyInput = new TextStringBuilder();
            distance.apply(emptyInput, null);
        });
    }

    @Test
    void testApplyWithNullSimilarityInput() {
        // Every combination involving a null SimilarityInput must be rejected,
        // regardless of whether the threshold is zero or unset (null).
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

    @Test
    void testApplyWithNullString() {
        assertThrows(IllegalArgumentException.class, () -> new LevenshteinDetailedDistance(0).apply((String) null, (String) null));
    }

    @Test
    void testConstructorWithNegativeThreshold() {
        assertThrows(IllegalArgumentException.class, () -> new LevenshteinDetailedDistance(-1));
    }

    @ParameterizedTest
    @MethodSource("org.apache.commons.text.similarity.SimilarityInputTest#similarityInputs()")
    void testCreatesLevenshteinDetailedDistanceTakingInteger6(final Class<?> cls) {
        // With a threshold of 0, any non-zero distance is reported as -1 (threshold exceeded).
        final LevenshteinDetailedDistance distance = new LevenshteinDetailedDistance(0);
        final LevenshteinResults results = distance.apply("", "Distance: 38, Insert: 0, Delete: 0, Substitute: 0");
        assertResults(results, -1, 0, 0, 0);

        // The CharSequence overload and the SimilarityInput overload must agree.
        assertEquals(results, distance.apply(SimilarityInputTest.build(cls, ""),
                SimilarityInputTest.build(cls, "Distance: 38, Insert: 0, Delete: 0, Substitute: 0")));
    }

    @ParameterizedTest
    @MethodSource("org.apache.commons.text.similarity.SimilarityInputTest#similarityInputs()")
    void testEquals(final Class<?> cls) {
        final LevenshteinDetailedDistance classBeingTested = LevenshteinDetailedDistance.getDefaultInstance();

        // "hello" -> "hallo": a single substitution.
        LevenshteinResults actualResult = classBeingTested.apply(SimilarityInputTest.build(cls, "hello"), SimilarityInputTest.build(cls, "hallo"));
        assertEquals(new LevenshteinResults(1, 0, 0, 1), actualResult);

        // The CharSequence overload and the SimilarityInput overload must produce equal results.
        assertEquals(classBeingTested.apply("zzzzzzzz", "hippo"),
                classBeingTested.apply(SimilarityInputTest.build(cls, "zzzzzzzz"), SimilarityInputTest.build(cls, "hippo")));

        // "zzzzzzzz" -> "hippo": 3 deletions and 5 substitutions.
        actualResult = classBeingTested.apply(SimilarityInputTest.build(cls, "zzzzzzzz"), SimilarityInputTest.build(cls, "hippo"));
        assertEquals(new LevenshteinResults(8, 0, 3, 5), actualResult);
        assertEquals(actualResult, actualResult); // a result must equal itself (reflexivity)

        // Two empty inputs: distance of zero with no operations.
        actualResult = classBeingTested.apply(SimilarityInputTest.build(cls, ""), SimilarityInputTest.build(cls, ""));
        assertEquals(new LevenshteinResults(0, 0, 0, 0), actualResult);
    }

    @ParameterizedTest
    @MethodSource("org.apache.commons.text.similarity.SimilarityInputTest#similarityInputs()")
    void testGetDefaultInstanceOne(final Class<?> cls) {
        final LevenshteinDetailedDistance distance = LevenshteinDetailedDistance.getDefaultInstance();
        final LevenshteinResults results = distance.apply(
                SimilarityInputTest.build(cls, "Distance: -2147483643, Insert: 0, Delete: 0, Substitute: 0"),
                SimilarityInputTest.build(cls, "Distance: 0, Insert: 2147483536, Delete: 0, Substitute: 0"));

        assertEquals(21, results.getDistance());
    }

    @ParameterizedTest
    @MethodSource("org.apache.commons.text.similarity.SimilarityInputTest#similarityInputs()")
    void testGetDefaultInstanceTwo(final Class<?> cls) {
        final LevenshteinDetailedDistance distance = LevenshteinDetailedDistance.getDefaultInstance();
        final LevenshteinResults results = distance.apply("Distance: 2147483647, Insert: 0, Delete: 0, Substitute: 0",
                "Distance: 0, Insert: 2147483647, Delete: 0, Substitute: 0");
        assertEquals(20, results.getDistance());

        // The SimilarityInput overload must produce a result equal to the CharSequence overload.
        assertEquals(results,
                distance.apply(SimilarityInputTest.build(cls, "Distance: 2147483647, Insert: 0, Delete: 0, Substitute: 0"),
                        SimilarityInputTest.build(cls, "Distance: 0, Insert: 2147483647, Delete: 0, Substitute: 0")));
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

    @ParameterizedTest
    @MethodSource("org.apache.commons.text.similarity.SimilarityInputTest#similarityInputs()")
    void testGetLevenshteinDetailedDistance_StringString(final Class<?> cls) {
        // Each case checks the unlimited (no-threshold) distance and its insert/delete/substitute breakdown.

        // Two empty inputs: no edits required.
        assertResults(UNLIMITED_DISTANCE.apply(SimilarityInputTest.build(cls, ""), SimilarityInputTest.build(cls, "")), 0, 0, 0, 0);

        // "" -> "a": one insertion.
        assertResults(UNLIMITED_DISTANCE.apply(SimilarityInputTest.build(cls, ""), SimilarityInputTest.build(cls, "a")), 1, 1, 0, 0);

        // "aaapppp" -> "": delete all seven characters.
        assertResults(UNLIMITED_DISTANCE.apply(SimilarityInputTest.build(cls, "aaapppp"), SimilarityInputTest.build(cls, "")), 7, 0, 7, 0);

        // "frog" -> "fog": delete the 'r'.
        assertResults(UNLIMITED_DISTANCE.apply(SimilarityInputTest.build(cls, "frog"), SimilarityInputTest.build(cls, "fog")), 1, 0, 1, 0);

        // "fly" -> "ant": substitute every character.
        assertResults(UNLIMITED_DISTANCE.apply(SimilarityInputTest.build(cls, "fly"), SimilarityInputTest.build(cls, "ant")), 3, 0, 0, 3);

        // "elephant" -> "hippo": 3 deletions and 4 substitutions.
        assertResults(UNLIMITED_DISTANCE.apply(SimilarityInputTest.build(cls, "elephant"), SimilarityInputTest.build(cls, "hippo")), 7, 0, 3, 4);

        // "hippo" -> "elephant": the mirror case, 3 insertions and 4 substitutions.
        assertResults(UNLIMITED_DISTANCE.apply(SimilarityInputTest.build(cls, "hippo"), SimilarityInputTest.build(cls, "elephant")), 7, 3, 0, 4);

        // "hippo" -> "zzzzzzzz": 3 insertions and 5 substitutions.
        assertResults(UNLIMITED_DISTANCE.apply(SimilarityInputTest.build(cls, "hippo"), SimilarityInputTest.build(cls, "zzzzzzzz")), 8, 3, 0, 5);

        // "zzzzzzzz" -> "hippo": the mirror case, 3 deletions and 5 substitutions.
        assertResults(UNLIMITED_DISTANCE.apply(SimilarityInputTest.build(cls, "zzzzzzzz"), SimilarityInputTest.build(cls, "hippo")), 8, 0, 3, 5);

        // "hello" -> "hallo": a single substitution.
        assertResults(UNLIMITED_DISTANCE.apply(SimilarityInputTest.build(cls, "hello"), SimilarityInputTest.build(cls, "hallo")), 1, 0, 0, 1);
    }

    @Test
    void testGetLevenshteinDetailedDistance_StringStringInt() {
        // Each case applies a threshold-limited distance. When the true distance exceeds the
        // threshold, the algorithm gives up and reports a distance of -1 with all counts zeroed.

        // Threshold large enough: the exact result is returned.
        assertResults(new LevenshteinDetailedDistance(0).apply("", ""), 0, 0, 0, 0);
        assertResults(new LevenshteinDetailedDistance(8).apply("aaapppp", ""), 7, 0, 7, 0);
        assertResults(new LevenshteinDetailedDistance(7).apply("aaapppp", ""), 7, 0, 7, 0);

        // Threshold 6 < distance 7: exceeded.
        assertResults(new LevenshteinDetailedDistance(6).apply("aaapppp", ""), -1, 0, 0, 0);

        // Threshold 0 with differing single characters: exceeded.
        assertResults(new LevenshteinDetailedDistance(0).apply("b", "a"), -1, 0, 0, 0);
        assertResults(new LevenshteinDetailedDistance(0).apply("a", "b"), -1, 0, 0, 0);

        // Identical inputs: distance 0, within any non-negative threshold.
        assertResults(new LevenshteinDetailedDistance(0).apply("aa", "aa"), 0, 0, 0, 0);
        assertResults(new LevenshteinDetailedDistance(2).apply("aa", "aa"), 0, 0, 0, 0);

        // "aaa" -> "bbb": three substitutions; only allowed when threshold >= 3.
        assertResults(new LevenshteinDetailedDistance(2).apply("aaa", "bbb"), -1, 0, 0, 0);
        assertResults(new LevenshteinDetailedDistance(3).apply("aaa", "bbb"), 3, 0, 0, 3);

        // Mixed delete + substitute cases within threshold.
        assertResults(new LevenshteinDetailedDistance(10).apply("aaaaaa", "b"), 6, 0, 5, 1);
        assertResults(new LevenshteinDetailedDistance(8).apply("aaapppp", "b"), 7, 0, 6, 1);

        // "a" -> "bbb": two insertions and one substitution; allowed when threshold >= 3.
        assertResults(new LevenshteinDetailedDistance(4).apply("a", "bbb"), 3, 2, 0, 1);
        assertResults(new LevenshteinDetailedDistance(7).apply("aaapppp", "b"), 7, 0, 6, 1);
        assertResults(new LevenshteinDetailedDistance(3).apply("a", "bbb"), 3, 2, 0, 1);

        // Thresholds too small for the required edits: all exceeded.
        assertResults(new LevenshteinDetailedDistance(2).apply("a", "bbb"), -1, 0, 0, 0);
        assertResults(new LevenshteinDetailedDistance(2).apply("bbb", "a"), -1, 0, 0, 0);
        assertResults(new LevenshteinDetailedDistance(6).apply("aaapppp", "b"), -1, 0, 0, 0);
        assertResults(new LevenshteinDetailedDistance(1).apply("a", "bbb"), -1, 0, 0, 0);
        assertResults(new LevenshteinDetailedDistance(1).apply("bbb", "a"), -1, 0, 0, 0);

        // Length difference alone (2) already exceeds threshold 1.
        assertResults(new LevenshteinDetailedDistance(1).apply("12345", "1234567"), -1, 0, 0, 0);
        assertResults(new LevenshteinDetailedDistance(1).apply("1234567", "12345"), -1, 0, 0, 0);

        // Small, within-threshold edits.
        assertResults(new LevenshteinDetailedDistance(1).apply("frog", "fog"), 1, 0, 1, 0);
        assertResults(new LevenshteinDetailedDistance(3).apply("fly", "ant"), 3, 0, 0, 3);

        // "elephant" <-> "hippo": distance 7, allowed only when threshold >= 7.
        assertResults(new LevenshteinDetailedDistance(7).apply("elephant", "hippo"), 7, 0, 3, 4);
        assertResults(new LevenshteinDetailedDistance(6).apply("elephant", "hippo"), -1, 0, 0, 0);
        assertResults(new LevenshteinDetailedDistance(7).apply("hippo", "elephant"), 7, 3, 0, 4);
        assertResults(new LevenshteinDetailedDistance(7).apply("hippo", "elephant"), 7, 3, 0, 4);
        assertResults(new LevenshteinDetailedDistance(6).apply("hippo", "elephant"), -1, 0, 0, 0);

        // "hippo" <-> "zzzzzzzz": distance 8.
        assertResults(new LevenshteinDetailedDistance(8).apply("hippo", "zzzzzzzz"), 8, 3, 0, 5);
        assertResults(new LevenshteinDetailedDistance(8).apply("zzzzzzzz", "hippo"), 8, 0, 3, 5);

        // "hello" -> "hallo": one substitution.
        assertResults(new LevenshteinDetailedDistance(1).apply("hello", "hallo"), 1, 0, 0, 1);

        // With Integer.MAX_VALUE the threshold never binds, so results match the unlimited algorithm.
        assertResults(new LevenshteinDetailedDistance(Integer.MAX_VALUE).apply("frog", "fog"), 1, 0, 1, 0);
        assertResults(new LevenshteinDetailedDistance(Integer.MAX_VALUE).apply("fly", "ant"), 3, 0, 0, 3);
        assertResults(new LevenshteinDetailedDistance(Integer.MAX_VALUE).apply("elephant", "hippo"), 7, 0, 3, 4);
        assertResults(new LevenshteinDetailedDistance(Integer.MAX_VALUE).apply("hippo", "elephant"), 7, 3, 0, 4);
        assertResults(new LevenshteinDetailedDistance(Integer.MAX_VALUE).apply("hippo", "zzzzzzzz"), 8, 3, 0, 5);
        assertResults(new LevenshteinDetailedDistance(Integer.MAX_VALUE).apply("zzzzzzzz", "hippo"), 8, 0, 3, 5);
        assertResults(new LevenshteinDetailedDistance(Integer.MAX_VALUE).apply("hello", "hallo"), 1, 0, 0, 1);
    }

    @Test
    void testGetThreshold() {
        final LevenshteinDetailedDistance distance = new LevenshteinDetailedDistance(0);

        assertEquals(0, distance.getThreshold());
    }

    @Test
    void testHashCode() {
        // Results that are logically equal must share a hash code.
        final LevenshteinDetailedDistance classBeingTested = LevenshteinDetailedDistance.getDefaultInstance();

        assertEquals(new LevenshteinResults(7, 0, 7, 0).hashCode(), classBeingTested.apply("aaapppp", "").hashCode());
        assertEquals(new LevenshteinResults(1, 0, 1, 0).hashCode(), classBeingTested.apply("frog", "fog").hashCode());
        assertEquals(new LevenshteinResults(7, 0, 3, 4).hashCode(), classBeingTested.apply("elephant", "hippo").hashCode());
    }

    @Test
    void testToString() {
        // Results that are logically equal must share a string representation.
        final LevenshteinDetailedDistance classBeingTested = LevenshteinDetailedDistance.getDefaultInstance();

        assertEquals(new LevenshteinResults(3, 0, 0, 3).toString(), classBeingTested.apply("fly", "ant").toString());
        assertEquals(new LevenshteinResults(7, 3, 0, 4).toString(), classBeingTested.apply("hippo", "elephant").toString());
        assertEquals(new LevenshteinResults(1, 1, 0, 0).toString(), classBeingTested.apply("", "a").toString());
    }

}
