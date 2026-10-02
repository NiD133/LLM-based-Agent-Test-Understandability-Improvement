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

    @SuppressWarnings("rawtypes")
    private static SimilarityInput input(final Class<?> cls, final String value) {
        return SimilarityInputTest.build(cls, value);
    }

    private static void assertDetailedResult(final int distance, final int inserts, final int deletes, final int substitutes,
            final LevenshteinResults result) {
        assertEquals(distance, result.getDistance());
        assertEquals(inserts, result.getInsertCount());
        assertEquals(deletes, result.getDeleteCount());
        assertEquals(substitutes, result.getSubstituteCount());
    }

    private static void assertLimitedDistance(final Integer threshold, final String left, final String right, final int distance, final int inserts,
            final int deletes, final int substitutes) {
        assertDetailedResult(distance, inserts, deletes, substitutes, new LevenshteinDetailedDistance(threshold).apply(left, right));
    }

    private static void assertUnlimitedDistance(final Class<?> cls, final String left, final String right, final int distance, final int inserts,
            final int deletes, final int substitutes) {
        assertDetailedResult(distance, inserts, deletes, substitutes, UNLIMITED_DISTANCE.apply(input(cls, left), input(cls, right)));
    }

    @Test
    void testApplyThrowsIllegalArgumentExceptionAndCreatesLevenshteinDetailedDistanceTakingInteger() {
        assertThrows(IllegalArgumentException.class, () -> {
            final LevenshteinDetailedDistance levenshteinDetailedDistance = new LevenshteinDetailedDistance(0);
            final CharSequence charSequence = new TextStringBuilder();
            levenshteinDetailedDistance.apply(charSequence, null);
        });
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
        final LevenshteinDetailedDistance levenshteinDetailedDistance = new LevenshteinDetailedDistance(0);
        final LevenshteinResults levenshteinResults = levenshteinDetailedDistance.apply("", "Distance: 38, Insert: 0, Delete: 0, Substitute: 0");

        assertDetailedResult(-1, 0, 0, 0, levenshteinResults);
        assertEquals(levenshteinResults, levenshteinDetailedDistance.apply(input(cls, ""),
                input(cls, "Distance: 38, Insert: 0, Delete: 0, Substitute: 0")));
    }

    @ParameterizedTest
    @MethodSource("org.apache.commons.text.similarity.SimilarityInputTest#similarityInputs()")
    void testEquals(final Class<?> cls) {
        final LevenshteinDetailedDistance classBeingTested = LevenshteinDetailedDistance.getDefaultInstance();
        LevenshteinResults actualResult = classBeingTested.apply(input(cls, "hello"), input(cls, "hallo"));
        LevenshteinResults expectedResult = new LevenshteinResults(1, 0, 0, 1);
        assertEquals(expectedResult, actualResult);

        assertEquals(classBeingTested.apply("zzzzzzzz", "hippo"), classBeingTested.apply(input(cls, "zzzzzzzz"), input(cls, "hippo")));
        actualResult = classBeingTested.apply(input(cls, "zzzzzzzz"), input(cls, "hippo"));
        expectedResult = new LevenshteinResults(8, 0, 3, 5);
        assertEquals(expectedResult, actualResult);
        assertEquals(actualResult, actualResult);

        actualResult = classBeingTested.apply(input(cls, ""), input(cls, ""));
        expectedResult = new LevenshteinResults(0, 0, 0, 0);
        assertEquals(expectedResult, actualResult);
    }

    @ParameterizedTest
    @MethodSource("org.apache.commons.text.similarity.SimilarityInputTest#similarityInputs()")
    void testGetDefaultInstanceOne(final Class<?> cls) {
        final LevenshteinDetailedDistance levenshteinDetailedDistance = LevenshteinDetailedDistance.getDefaultInstance();
        final LevenshteinResults levenshteinResults = levenshteinDetailedDistance.apply(
                input(cls, "Distance: -2147483643, Insert: 0, Delete: 0, Substitute: 0"),
                input(cls, "Distance: 0, Insert: 2147483536, Delete: 0, Substitute: 0"));

        assertEquals(21, levenshteinResults.getDistance());
    }

    @ParameterizedTest
    @MethodSource("org.apache.commons.text.similarity.SimilarityInputTest#similarityInputs()")
    void testGetDefaultInstanceTwo(final Class<?> cls) {
        final LevenshteinDetailedDistance levenshteinDetailedDistance = LevenshteinDetailedDistance.getDefaultInstance();
        final LevenshteinResults levenshteinResults = levenshteinDetailedDistance.apply(
                "Distance: 2147483647, Insert: 0, Delete: 0, Substitute: 0",
                "Distance: 0, Insert: 2147483647, Delete: 0, Substitute: 0");

        assertEquals(20, levenshteinResults.getDistance());
        assertEquals(levenshteinResults, levenshteinDetailedDistance.apply(
                input(cls, "Distance: 2147483647, Insert: 0, Delete: 0, Substitute: 0"),
                input(cls, "Distance: 0, Insert: 2147483647, Delete: 0, Substitute: 0")));
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
        assertUnlimitedDistance(cls, "", "", 0, 0, 0, 0);
        assertUnlimitedDistance(cls, "", "a", 1, 1, 0, 0);
        assertUnlimitedDistance(cls, "aaapppp", "", 7, 0, 7, 0);
        assertUnlimitedDistance(cls, "frog", "fog", 1, 0, 1, 0);
        assertUnlimitedDistance(cls, "fly", "ant", 3, 0, 0, 3);
        assertUnlimitedDistance(cls, "elephant", "hippo", 7, 0, 3, 4);
        assertUnlimitedDistance(cls, "hippo", "elephant", 7, 3, 0, 4);
        assertUnlimitedDistance(cls, "hippo", "zzzzzzzz", 8, 3, 0, 5);
        assertUnlimitedDistance(cls, "zzzzzzzz", "hippo", 8, 0, 3, 5);
        assertUnlimitedDistance(cls, "hello", "hallo", 1, 0, 0, 1);
    }

    @Test
    void testGetLevenshteinDetailedDistance_StringStringInt() {
        assertLimitedDistance(0, "", "", 0, 0, 0, 0);
        assertLimitedDistance(8, "aaapppp", "", 7, 0, 7, 0);
        assertLimitedDistance(7, "aaapppp", "", 7, 0, 7, 0);
        assertLimitedDistance(6, "aaapppp", "", -1, 0, 0, 0);
        assertLimitedDistance(0, "b", "a", -1, 0, 0, 0);
        assertLimitedDistance(0, "a", "b", -1, 0, 0, 0);
        assertLimitedDistance(0, "aa", "aa", 0, 0, 0, 0);
        assertLimitedDistance(2, "aa", "aa", 0, 0, 0, 0);
        assertLimitedDistance(2, "aaa", "bbb", -1, 0, 0, 0);
        assertLimitedDistance(3, "aaa", "bbb", 3, 0, 0, 3);
        assertLimitedDistance(10, "aaaaaa", "b", 6, 0, 5, 1);
        assertLimitedDistance(8, "aaapppp", "b", 7, 0, 6, 1);
        assertLimitedDistance(4, "a", "bbb", 3, 2, 0, 1);
        assertLimitedDistance(7, "aaapppp", "b", 7, 0, 6, 1);
        assertLimitedDistance(3, "a", "bbb", 3, 2, 0, 1);
        assertLimitedDistance(2, "a", "bbb", -1, 0, 0, 0);
        assertLimitedDistance(2, "bbb", "a", -1, 0, 0, 0);
        assertLimitedDistance(6, "aaapppp", "b", -1, 0, 0, 0);
        assertLimitedDistance(1, "a", "bbb", -1, 0, 0, 0);
        assertLimitedDistance(1, "bbb", "a", -1, 0, 0, 0);
        assertLimitedDistance(1, "12345", "1234567", -1, 0, 0, 0);
        assertLimitedDistance(1, "1234567", "12345", -1, 0, 0, 0);
        assertLimitedDistance(1, "frog", "fog", 1, 0, 1, 0);
        assertLimitedDistance(3, "fly", "ant", 3, 0, 0, 3);
        assertLimitedDistance(7, "elephant", "hippo", 7, 0, 3, 4);
        assertLimitedDistance(6, "elephant", "hippo", -1, 0, 0, 0);
        assertLimitedDistance(7, "hippo", "elephant", 7, 3, 0, 4);
        assertLimitedDistance(7, "hippo", "elephant", 7, 3, 0, 4);
        assertLimitedDistance(6, "hippo", "elephant", -1, 0, 0, 0);
        assertLimitedDistance(8, "hippo", "zzzzzzzz", 8, 3, 0, 5);
        assertLimitedDistance(8, "zzzzzzzz", "hippo", 8, 0, 3, 5);
        assertLimitedDistance(1, "hello", "hallo", 1, 0, 0, 1);
        assertLimitedDistance(Integer.MAX_VALUE, "frog", "fog", 1, 0, 1, 0);
        assertLimitedDistance(Integer.MAX_VALUE, "fly", "ant", 3, 0, 0, 3);
        assertLimitedDistance(Integer.MAX_VALUE, "elephant", "hippo", 7, 0, 3, 4);
        assertLimitedDistance(Integer.MAX_VALUE, "hippo", "elephant", 7, 3, 0, 4);
        assertLimitedDistance(Integer.MAX_VALUE, "hippo", "zzzzzzzz", 8, 3, 0, 5);
        assertLimitedDistance(Integer.MAX_VALUE, "zzzzzzzz", "hippo", 8, 0, 3, 5);
        assertLimitedDistance(Integer.MAX_VALUE, "hello", "hallo", 1, 0, 0, 1);
    }

    @Test
    void testGetThreshold() {
        final LevenshteinDetailedDistance levenshteinDetailedDistance = new LevenshteinDetailedDistance(0);

        assertEquals(0, levenshteinDetailedDistance.getThreshold());
    }

    @Test
    void testHashCode() {
        final LevenshteinDetailedDistance classBeingTested = LevenshteinDetailedDistance.getDefaultInstance();

        LevenshteinResults actualResult = classBeingTested.apply("aaapppp", "");
        LevenshteinResults expectedResult = new LevenshteinResults(7, 0, 7, 0);
        assertEquals(expectedResult.hashCode(), actualResult.hashCode());

        actualResult = classBeingTested.apply("frog", "fog");
        expectedResult = new LevenshteinResults(1, 0, 1, 0);
        assertEquals(expectedResult.hashCode(), actualResult.hashCode());

        actualResult = classBeingTested.apply("elephant", "hippo");
        expectedResult = new LevenshteinResults(7, 0, 3, 4);
        assertEquals(expectedResult.hashCode(), actualResult.hashCode());
    }

    @Test
    void testToString() {
        final LevenshteinDetailedDistance classBeingTested = LevenshteinDetailedDistance.getDefaultInstance();

        LevenshteinResults actualResult = classBeingTested.apply("fly", "ant");
        LevenshteinResults expectedResult = new LevenshteinResults(3, 0, 0, 3);
        assertEquals(expectedResult.toString(), actualResult.toString());

        actualResult = classBeingTested.apply("hippo", "elephant");
        expectedResult = new LevenshteinResults(7, 3, 0, 4);
        assertEquals(expectedResult.toString(), actualResult.toString());

        actualResult = classBeingTested.apply("", "a");
        expectedResult = new LevenshteinResults(1, 1, 0, 0);
        assertEquals(expectedResult.toString(), actualResult.toString());
    }

}
