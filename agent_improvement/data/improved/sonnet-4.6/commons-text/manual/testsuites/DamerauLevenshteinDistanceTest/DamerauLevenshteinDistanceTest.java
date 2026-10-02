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

import java.util.Arrays;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests {@link DamerauLevenshteinDistance}.
 *
 * <p>The Damerau-Levenshtein distance counts the minimum number of single-character
 * edits (insertions, deletions, substitutions, and transpositions of adjacent characters)
 * required to transform one string into another. This distance is symmetric: the cost
 * of transforming A into B equals the cost of transforming B into A.</p>
 *
 * <p>Two modes are tested:
 * <ul>
 *   <li><b>Unlimited</b>: always computes the exact distance (no threshold).</li>
 *   <li><b>Limited</b>: returns the distance only when it is within a given threshold;
 *       returns {@code -1} when the distance exceeds the threshold.</li>
 * </ul>
 * </p>
 */
public class DamerauLevenshteinDistanceTest {

    /** Shared instance with no threshold — uses the unlimited algorithm. */
    private static DamerauLevenshteinDistance defaultInstance;

    @BeforeAll
    static void createInstance() {
        defaultInstance = new DamerauLevenshteinDistance();
    }

    /**
     * Provides test cases for the threshold-limited algorithm.
     *
     * <p>Each row is {@code (left, right, threshold, expectedDistance)}.
     * When the true distance exceeds the threshold, {@code expectedDistance} is {@code -1}.</p>
     */
    static Stream<Arguments> limitedDamerauLevenshteinDistanceTestCases() {
        return Stream.of(
                // Empty-string edge cases: distance equals the length of the non-empty string
                Arguments.of("", "test", 10, 4),   // threshold high enough → exact distance returned
                Arguments.of("test", "", 10, 4),
                Arguments.of("", "test", 2, -1),   // threshold too low → -1 (distance 4 > threshold 2)
                Arguments.of("test", "", 2, -1),

                // Distance exceeds threshold → -1
                Arguments.of("testing long string", "testing", 2, -1),
                Arguments.of("kitten", "sitting", 1, -1),
                Arguments.of("algorithm", "logarithm", 1, -1),
                Arguments.of("programming", "porgramming", 0, -1),
                Arguments.of("transform", "transfrom", 0, -1),
                Arguments.of("password", "passwrod", 0, -1),
                Arguments.of("occurrence", "occurence", 0, -1),
                Arguments.of("beginning", "begining", 0, -1),
                Arguments.of("AAAAAAAAAA", "BBBBBBBBBB", 5, -1),
                Arguments.of("aaaaabbbbbccccc", "cccccbbbbbaaaaa", 5, -1),
                Arguments.of("pneumonoultramicroscopicsilicovolcanoconiosiss", "pneumonoultramicroscopicsilicovolcanoconiosis", 0, -1),

                // Single transposition or substitution within threshold → exact distance returned
                Arguments.of("computer", "comptuer", 1, 1),    // one transposition
                Arguments.of("receive", "recieve", 3, 1),       // one transposition (ie → ei)
                Arguments.of("test", "tset", 1, 1),             // one transposition (es → se)
                Arguments.of("example", "exmaple", 3, 1),       // one transposition
                Arguments.of("information", "infromation", 1, 1), // one transposition
                Arguments.of("development", "developemnt", 3, 1),
                Arguments.of("separate", "seperate", 1, 1),
                Arguments.of("definitely", "definately", 3, 1),
                Arguments.of("necessary", "neccessary", 1, 1),
                Arguments.of("government", "goverment", 1, 1),
                Arguments.of("thequickbrownfoxjumpsoverthelazydog", "thequickbrownfoxjumpsovrethelazydog", 1, 1),
                Arguments.of("antidisestablishmentarianism", "antidisestablishmentarianisn", 3, 1),
                Arguments.of("supercalifragilisticexpialidocious", "supercalifragilisticexpialidocous", 3, 1),

                // Multiple edits within threshold
                Arguments.of("saturday", "sunday", 3, 3),       // 3 edits, fits threshold of 3
                Arguments.of("hello", "world", 6, 4),           // 4 edits, fits threshold of 6
                Arguments.of("restaurant", "restaraunt", 4, 2),
                Arguments.of("abababababab", "babababababa", 2, 2),
                Arguments.of("xyxyxyxyxy", "yxyxyxyxyx", 4, 2),
                Arguments.of("abcdefghijklmnop", "ponmlkjihgfedcba", 17, 15),
                Arguments.of("abcdefg", "gfedcba", 6, 6)
        );
    }

    /**
     * Wraps each {@link #limitedDamerauLevenshteinDistanceTestCases()} entry with each
     * available {@link SimilarityInput} implementation class, producing a combined stream
     * for parameterized testing across different input types.
     */
    static Stream<Arguments> limitedDamerauLevenshteinDistanceTestCases_SimilarityInput() {
        return SimilarityInputTest.similarityInputs()
                .flatMap(cls -> limitedDamerauLevenshteinDistanceTestCases().map(arguments -> {
                    final Object[] values = Arrays.copyOf(arguments.get(), arguments.get().length + 1);
                    values[values.length - 1] = cls;
                    return Arguments.of(values);
                }));
    }

    /**
     * Provides test cases for the unlimited algorithm.
     *
     * <p>Each row is {@code (left, right, expectedDistance)}. The unlimited algorithm
     * always returns the exact distance regardless of how large it is.</p>
     */
    static Stream<Arguments> unlimitedDamerauLevenshteinDistanceTestCases() {
        return Stream.of(
                // Empty-string edge cases: distance equals the length of the non-empty string
                Arguments.of("", "test", 4),
                Arguments.of("test", "", 4),

                // Common English misspellings — most require exactly one edit
                Arguments.of("computer", "comptuer", 1),    // one transposition: "ue" → "eu"
                Arguments.of("receive", "recieve", 1),       // one transposition: "ie" → "ei"
                Arguments.of("programming", "porgramming", 1), // one transposition
                Arguments.of("test", "tset", 1),
                Arguments.of("example", "exmaple", 1),
                Arguments.of("transform", "transfrom", 1),
                Arguments.of("information", "infromation", 1),
                Arguments.of("development", "developemnt", 1),
                Arguments.of("password", "passwrod", 1),
                Arguments.of("separate", "seperate", 1),
                Arguments.of("definitely", "definately", 1),
                Arguments.of("occurrence", "occurence", 1),
                Arguments.of("necessary", "neccessary", 1),
                Arguments.of("beginning", "begining", 1),
                Arguments.of("government", "goverment", 1),
                Arguments.of("thequickbrownfoxjumpsoverthelazydog", "thequickbrownfoxjumpsovrethelazydog", 1),
                Arguments.of("antidisestablishmentarianism", "antidisestablishmentarianisn", 1),
                Arguments.of("supercalifragilisticexpialidocious", "supercalifragilisticexpialidocous", 1),
                Arguments.of("pneumonoultramicroscopicsilicovolcanoconiosiss", "pneumonoultramicroscopicsilicovolcanoconiosis", 1),

                // Multiple edits required
                Arguments.of("restaurant", "restaraunt", 2),
                Arguments.of("abababababab", "babababababa", 2),
                Arguments.of("xyxyxyxyxy", "yxyxyxyxyx", 2),
                Arguments.of("kitten", "sitting", 3),
                Arguments.of("saturday", "sunday", 3),
                Arguments.of("algorithm", "logarithm", 3),
                Arguments.of("hello", "world", 4),
                Arguments.of("AAAAAAAAAA", "BBBBBBBBBB", 10), // no characters in common
                Arguments.of("aaaaabbbbbccccc", "cccccbbbbbaaaaa", 10),
                Arguments.of("abcdefg", "gfedcba", 6),
                Arguments.of("abcdefghijklmnop", "ponmlkjihgfedcba", 15)
        );
    }

    /**
     * Wraps each {@link #unlimitedDamerauLevenshteinDistanceTestCases()} entry with each
     * available {@link SimilarityInput} implementation class, producing a combined stream
     * for parameterized testing across different input types.
     */
    static Stream<Arguments> unlimitedDamerauLevenshteinDistanceTestCases_SimilarityInput() {
        return SimilarityInputTest.similarityInputs()
                .flatMap(cls -> unlimitedDamerauLevenshteinDistanceTestCases().map(arguments -> {
                    final Object[] values = Arrays.copyOf(arguments.get(), arguments.get().length + 1);
                    values[values.length - 1] = cls;
                    return Arguments.of(values);
                }));
    }

    /**
     * Verifies that the unlimited algorithm returns the correct distance for String inputs.
     *
     * <p>Both directions (left→right and right→left) are asserted to confirm the
     * symmetric property of the Damerau-Levenshtein distance.</p>
     */
    @ParameterizedTest(name = "DamerauLevenshteinDistance.unlimitedCompare(\"{0}\", \"{1}\") should return {2}")
    @MethodSource("unlimitedDamerauLevenshteinDistanceTestCases")
    void testUnlimitedDistanceBetweenStrings(final String left, final String right, final int expectedDistance) {
        final int leftRightDistance = defaultInstance.apply(left, right);
        final int rightLeftDistance = defaultInstance.apply(right, left);
        assertEquals(expectedDistance, leftRightDistance);
        assertEquals(expectedDistance, rightLeftDistance); // distance is symmetric
    }

    /**
     * Verifies that the threshold-limited algorithm returns the correct distance (or {@code -1}
     * when the distance exceeds the threshold) for String inputs.
     *
     * <p>Both directions (left→right and right→left) are asserted to confirm symmetry.</p>
     */
    @ParameterizedTest(name = "DamerauLevenshteinDistance.limitedCompare(\"{0}\", \"{1}\") should return {2}")
    @MethodSource("limitedDamerauLevenshteinDistanceTestCases")
    void testLimitedDistanceBetweenStrings(final String left, final String right, final int threshold, final int expectedDistance) {
        final DamerauLevenshteinDistance thresholdedInstance = new DamerauLevenshteinDistance(threshold);
        final int leftRightDistance = thresholdedInstance.apply(left, right);
        final int rightLeftDistance = thresholdedInstance.apply(right, left);
        assertEquals(expectedDistance, leftRightDistance);
        assertEquals(expectedDistance, rightLeftDistance); // distance is symmetric
    }

    /**
     * Verifies that the unlimited algorithm returns the correct distance for generic
     * {@link SimilarityInput} instances, covering all registered {@link SimilarityInput}
     * implementation types.
     */
    @ParameterizedTest(name = "DamerauLevenshteinDistance.unlimitedCompare(\"{0}\", \"{1}\") should return {2} ({3})")
    @MethodSource("unlimitedDamerauLevenshteinDistanceTestCases_SimilarityInput")
    void testUnlimitedDistanceWithSimilarityInput(final String left, final String right, final int expectedDistance, final Class<?> cls) {
        final SimilarityInput<Object> leftInput = SimilarityInputTest.build(cls, left);
        final SimilarityInput<Object> rightInput = SimilarityInputTest.build(cls, right);
        final int leftRightDistance = defaultInstance.apply(leftInput, rightInput);
        final int rightLeftDistance = defaultInstance.apply(rightInput, leftInput);
        assertEquals(expectedDistance, leftRightDistance);
        assertEquals(expectedDistance, rightLeftDistance); // distance is symmetric
    }

    /**
     * Verifies that the threshold-limited algorithm returns the correct distance (or {@code -1})
     * for generic {@link SimilarityInput} instances, covering all registered implementation types.
     */
    @ParameterizedTest(name = "DamerauLevenshteinDistance.limitedCompare(\"{0}\", \"{1}\") should return {2}")
    @MethodSource("limitedDamerauLevenshteinDistanceTestCases_SimilarityInput")
    void testLimitedDistanceWithSimilarityInput(final String left, final String right, final int threshold, final int expectedDistance,
            final Class<?> cls) {
        final DamerauLevenshteinDistance thresholdedInstance = new DamerauLevenshteinDistance(threshold);
        final SimilarityInput<Object> leftInput = SimilarityInputTest.build(cls, left);
        final SimilarityInput<Object> rightInput = SimilarityInputTest.build(cls, right);
        final int leftRightDistance = thresholdedInstance.apply(leftInput, rightInput);
        final int rightLeftDistance = thresholdedInstance.apply(rightInput, leftInput);
        assertEquals(expectedDistance, leftRightDistance);
        assertEquals(expectedDistance, rightLeftDistance); // distance is symmetric
    }

    /**
     * Verifies that the no-arg constructor creates an instance with a {@code null} threshold,
     * which selects the unlimited algorithm.
     */
    @Test
    void testGetThresholdDirectlyAfterObjectInstantiation() {
        assertNull(defaultInstance.getThreshold());
    }

    /**
     * Verifies that the threshold passed to the constructor is returned correctly by {@link DamerauLevenshteinDistance#getThreshold()}.
     */
    @Test
    void testGetThresholdIsCorrect() {
        final DamerauLevenshteinDistance distance = new DamerauLevenshteinDistance(10);
        assertEquals(10, distance.getThreshold());
    }

    /**
     * Verifies that a negative threshold causes an {@link IllegalArgumentException} at construction time.
     */
    @Test
    void testInvalidThresholdThrows() {
        assertThrows(IllegalArgumentException.class, () -> new DamerauLevenshteinDistance(-1));
    }

    /**
     * Verifies that passing {@code null} as either input to the threshold-limited instance
     * throws an {@link IllegalArgumentException}, for both the {@code CharSequence} and
     * {@link SimilarityInput} overloads.
     */
    @Test
    void testNullInputsThrowLimited() {
        final DamerauLevenshteinDistance instance = new DamerauLevenshteinDistance(10);
        assertThrows(IllegalArgumentException.class, () -> instance.apply(null, "test"));
        assertThrows(IllegalArgumentException.class, () -> instance.apply("test", null));
        assertThrows(IllegalArgumentException.class, () -> instance.apply(null, SimilarityInput.input("test")));
        assertThrows(IllegalArgumentException.class, () -> instance.apply(SimilarityInput.input("test"), null));
    }

    /**
     * Verifies that passing {@code null} as either input to the unlimited instance
     * throws an {@link IllegalArgumentException}, for both the {@code CharSequence} and
     * {@link SimilarityInput} overloads.
     */
    @Test
    void testNullInputsThrowUnlimited() {
        assertThrows(IllegalArgumentException.class, () -> defaultInstance.apply(null, "test"));
        assertThrows(IllegalArgumentException.class, () -> defaultInstance.apply("test", null));
        assertThrows(IllegalArgumentException.class, () -> defaultInstance.apply(null, SimilarityInput.input("test")));
        assertThrows(IllegalArgumentException.class, () -> defaultInstance.apply(SimilarityInput.input("test"), null));
    }
}
