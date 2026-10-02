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
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests {@link DamerauLevenshteinDistance}.
 *
 * <p>
 * The distance is expected to be symmetric, so every parameterized test applies the algorithm in both
 * directions ({@code left -> right} and {@code right -> left}) and checks that both yield the same result.
 * </p>
 */
public class DamerauLevenshteinDistanceTest {

    /** A reusable instance with no threshold (uses the unlimited variant of the algorithm). */
    private static DamerauLevenshteinDistance unlimitedInstance;

    @BeforeAll
    static void createInstance() {
        unlimitedInstance = new DamerauLevenshteinDistance();
    }

    /**
     * Test cases for the threshold-limited algorithm.
     * <p>
     * Each row is: {@code (left, right, threshold, expectedDistance)}, where {@code expectedDistance} is
     * {@code -1} when the real distance exceeds the threshold.
     * </p>
     */
    static Stream<Arguments> limitedDamerauLevenshteinDistanceTestCases() {
        return Stream.of(
                Arguments.of("", "test", 10, 4),
                Arguments.of("test", "", 10, 4),
                Arguments.of("", "test", 2, -1),
                Arguments.of("test", "", 2, -1),
                Arguments.of("testing long string", "testing", 2, -1),
                Arguments.of("kitten", "sitting", 1, -1),
                Arguments.of("saturday", "sunday", 3, 3),
                Arguments.of("hello", "world", 6, 4),
                Arguments.of("algorithm", "logarithm", 1, -1),
                Arguments.of("computer", "comptuer", 1, 1),
                Arguments.of("receive", "recieve", 3, 1),
                Arguments.of("programming", "porgramming", 0, -1),
                Arguments.of("test", "tset", 1, 1),
                Arguments.of("example", "exmaple", 3, 1),
                Arguments.of("transform", "transfrom", 0, -1),
                Arguments.of("information", "infromation", 1, 1),
                Arguments.of("development", "developemnt", 3, 1),
                Arguments.of("password", "passwrod", 0, -1),
                Arguments.of("separate", "seperate", 1, 1),
                Arguments.of("definitely", "definately", 3, 1),
                Arguments.of("occurrence", "occurence", 0, -1),
                Arguments.of("necessary", "neccessary", 1, 1),
                Arguments.of("restaurant", "restaraunt", 4, 2),
                Arguments.of("beginning", "begining", 0, -1),
                Arguments.of("government", "goverment", 1, 1),
                Arguments.of("abcdefghijklmnop", "ponmlkjihgfedcba", 17, 15),
                Arguments.of("AAAAAAAAAA", "BBBBBBBBBB", 5, -1),
                Arguments.of("abababababab", "babababababa", 2, 2),
                Arguments.of("supercalifragilisticexpialidocious", "supercalifragilisticexpialidocous", 3, 1),
                Arguments.of("pneumonoultramicroscopicsilicovolcanoconiosiss", "pneumonoultramicroscopicsilicovolcanoconiosis", 0, -1),
                Arguments.of("abcdefg", "gfedcba", 6, 6),
                Arguments.of("xyxyxyxyxy", "yxyxyxyxyx", 4, 2),
                Arguments.of("aaaaabbbbbccccc", "cccccbbbbbaaaaa", 5, -1),
                Arguments.of("thequickbrownfoxjumpsoverthelazydog", "thequickbrownfoxjumpsovrethelazydog", 1, 1),
                Arguments.of("antidisestablishmentarianism", "antidisestablishmentarianisn", 3, 1)
        );
    }

    /**
     * The limited test cases, each extended with a {@link SimilarityInput} implementation class so the
     * algorithm can be exercised against every supported input type.
     * <p>
     * Each row is: {@code (left, right, threshold, expectedDistance, similarityInputClass)}.
     * </p>
     */
    static Stream<Arguments> limitedDamerauLevenshteinDistanceTestCases_SimilarityInput() {
        return appendSimilarityInputClass(DamerauLevenshteinDistanceTest::limitedDamerauLevenshteinDistanceTestCases);
    }

    /**
     * Test cases for the unlimited (no threshold) algorithm.
     * <p>
     * Each row is: {@code (left, right, expectedDistance)}.
     * </p>
     */
    static Stream<Arguments> unlimitedDamerauLevenshteinDistanceTestCases() {
        return Stream.of(
                Arguments.of("", "test", 4),
                Arguments.of("test", "", 4),
                Arguments.of("kitten", "sitting", 3),
                Arguments.of("saturday", "sunday", 3),
                Arguments.of("hello", "world", 4),
                Arguments.of("algorithm", "logarithm", 3),
                Arguments.of("computer", "comptuer", 1),
                Arguments.of("receive", "recieve", 1),
                Arguments.of("programming", "porgramming", 1),
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
                Arguments.of("restaurant", "restaraunt", 2),
                Arguments.of("beginning", "begining", 1),
                Arguments.of("government", "goverment", 1),
                Arguments.of("abcdefghijklmnop", "ponmlkjihgfedcba", 15),
                Arguments.of("AAAAAAAAAA", "BBBBBBBBBB", 10),
                Arguments.of("abababababab", "babababababa", 2),
                Arguments.of("supercalifragilisticexpialidocious", "supercalifragilisticexpialidocous", 1),
                Arguments.of("pneumonoultramicroscopicsilicovolcanoconiosiss", "pneumonoultramicroscopicsilicovolcanoconiosis", 1),
                Arguments.of("abcdefg", "gfedcba", 6),
                Arguments.of("xyxyxyxyxy", "yxyxyxyxyx", 2),
                Arguments.of("aaaaabbbbbccccc", "cccccbbbbbaaaaa", 10),
                Arguments.of("thequickbrownfoxjumpsoverthelazydog", "thequickbrownfoxjumpsovrethelazydog", 1),
                Arguments.of("antidisestablishmentarianism", "antidisestablishmentarianisn", 1)
        );
    }

    /**
     * The unlimited test cases, each extended with a {@link SimilarityInput} implementation class so the
     * algorithm can be exercised against every supported input type.
     * <p>
     * Each row is: {@code (left, right, expectedDistance, similarityInputClass)}.
     * </p>
     */
    static Stream<Arguments> unlimitedDamerauLevenshteinDistanceTestCases_SimilarityInput() {
        return appendSimilarityInputClass(DamerauLevenshteinDistanceTest::unlimitedDamerauLevenshteinDistanceTestCases);
    }

    /**
     * Builds the cross product of the given base test cases with every {@link SimilarityInput} implementation
     * class, appending the class as an extra trailing argument to each case.
     *
     * @param baseCases supplier of the base cases (a supplier is required because the stream is consumed once per class).
     * @return the base cases combined with each similarity input class.
     */
    private static Stream<Arguments> appendSimilarityInputClass(final Supplier<Stream<Arguments>> baseCases) {
        return SimilarityInputTest.similarityInputs()
                .flatMap(cls -> baseCases.get().map(arguments -> {
                    final Object[] valuesWithClass = Arrays.copyOf(arguments.get(), arguments.get().length + 1);
                    valuesWithClass[valuesWithClass.length - 1] = cls;
                    return Arguments.of(valuesWithClass);
                }));
    }

    /**
     * Asserts that applying {@code distance} to the two strings yields {@code expectedDistance} regardless of
     * argument order.
     */
    private static void assertSymmetricDistance(final int expectedDistance, final DamerauLevenshteinDistance distance,
            final String left, final String right) {
        assertEquals(expectedDistance, distance.apply(left, right));
        assertEquals(expectedDistance, distance.apply(right, left));
    }

    /**
     * Asserts that applying {@code distance} to the two similarity inputs yields {@code expectedDistance}
     * regardless of argument order.
     */
    private static void assertSymmetricDistance(final int expectedDistance, final DamerauLevenshteinDistance distance,
            final SimilarityInput<Object> left, final SimilarityInput<Object> right) {
        assertEquals(expectedDistance, distance.apply(left, right));
        assertEquals(expectedDistance, distance.apply(right, left));
    }

    @ParameterizedTest(name = "DamerauLevenshteinDistance.unlimitedCompare(\"{0}\", \"{1}\") should return {2}")
    @MethodSource("unlimitedDamerauLevenshteinDistanceTestCases")
    void testUnlimitedCompare(final String left, final String right, final int expectedDistance) {
        assertSymmetricDistance(expectedDistance, unlimitedInstance, left, right);
    }

    @ParameterizedTest(name = "DamerauLevenshteinDistance.limitedCompare(\"{0}\", \"{1}\") should return {2}")
    @MethodSource("limitedDamerauLevenshteinDistanceTestCases")
    void testLimitedCompare(final String left, final String right, final int threshold, final int expectedDistance) {
        final DamerauLevenshteinDistance limitedInstance = new DamerauLevenshteinDistance(threshold);
        assertSymmetricDistance(expectedDistance, limitedInstance, left, right);
    }

    @ParameterizedTest(name = "DamerauLevenshteinDistance.unlimitedCompare(\"{0}\", \"{1}\") should return {2} ({3})")
    @MethodSource("unlimitedDamerauLevenshteinDistanceTestCases_SimilarityInput")
    void testUnlimitedCompare_SimilarityInput(final String left, final String right, final int expectedDistance, final Class<?> cls) {
        final SimilarityInput<Object> leftInput = SimilarityInputTest.build(cls, left);
        final SimilarityInput<Object> rightInput = SimilarityInputTest.build(cls, right);
        assertSymmetricDistance(expectedDistance, unlimitedInstance, leftInput, rightInput);
    }

    @ParameterizedTest(name = "DamerauLevenshteinDistance.limitedCompare(\"{0}\", \"{1}\") should return {2}")
    @MethodSource("limitedDamerauLevenshteinDistanceTestCases_SimilarityInput")
    void testLimitedCompare_SimilarityInput(final String left, final String right, final int threshold, final int expectedDistance,
            final Class<?> cls) {
        final DamerauLevenshteinDistance limitedInstance = new DamerauLevenshteinDistance(threshold);
        final SimilarityInput<Object> leftInput = SimilarityInputTest.build(cls, left);
        final SimilarityInput<Object> rightInput = SimilarityInputTest.build(cls, right);
        assertSymmetricDistance(expectedDistance, limitedInstance, leftInput, rightInput);
    }

    @Test
    void testGetThresholdDirectlyAfterObjectInstantiation() {
        assertNull(unlimitedInstance.getThreshold());
    }

    @Test
    void testGetThresholdIsCorrect() {
        final DamerauLevenshteinDistance distance = new DamerauLevenshteinDistance(10);
        assertEquals(10, distance.getThreshold());
    }

    @Test
    void testInvalidThresholdThrows() {
        assertThrows(IllegalArgumentException.class, () -> new DamerauLevenshteinDistance(-1));
    }

    @Test
    void testNullInputsThrowLimited() {
        final DamerauLevenshteinDistance limitedInstance = new DamerauLevenshteinDistance(10);
        assertThrows(IllegalArgumentException.class, () -> limitedInstance.apply(null, "test"));
        assertThrows(IllegalArgumentException.class, () -> limitedInstance.apply("test", null));
        assertThrows(IllegalArgumentException.class, () -> limitedInstance.apply(null, SimilarityInput.input("test")));
        assertThrows(IllegalArgumentException.class, () -> limitedInstance.apply(SimilarityInput.input("test"), null));
    }

    @Test
    void testNullInputsThrowUnlimited() {
        assertThrows(IllegalArgumentException.class, () -> unlimitedInstance.apply(null, "test"));
        assertThrows(IllegalArgumentException.class, () -> unlimitedInstance.apply("test", null));
        assertThrows(IllegalArgumentException.class, () -> unlimitedInstance.apply(null, SimilarityInput.input("test")));
        assertThrows(IllegalArgumentException.class, () -> unlimitedInstance.apply(SimilarityInput.input("test"), null));
    }
}
