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
 */
public class DamerauLevenshteinDistanceTest {

    private static final String TEST = "test";
    private static final int POSITIVE_THRESHOLD = 10;

    private static DamerauLevenshteinDistance defaultInstance;

    @BeforeAll
    static void createInstance() {
        defaultInstance = new DamerauLevenshteinDistance();
    }

    static Stream<Arguments> limitedDamerauLevenshteinDistanceTestCases() {
        return Stream.of(
                // Empty input handling and threshold clamping.
                Arguments.of("", TEST, 10, 4),
                Arguments.of(TEST, "", 10, 4),
                Arguments.of("", TEST, 2, -1),
                Arguments.of(TEST, "", 2, -1),

                // Common substitutions, insertions, deletions, and transpositions.
                Arguments.of("testing long string", "testing", 2, -1),
                Arguments.of("kitten", "sitting", 1, -1),
                Arguments.of("saturday", "sunday", 3, 3),
                Arguments.of("hello", "world", 6, 4),
                Arguments.of("algorithm", "logarithm", 1, -1),
                Arguments.of("computer", "comptuer", 1, 1),
                Arguments.of("receive", "recieve", 3, 1),
                Arguments.of("programming", "porgramming", 0, -1),
                Arguments.of(TEST, "tset", 1, 1),
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

                // Longer inputs and repeated-character patterns.
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

    static Stream<Arguments> limitedDamerauLevenshteinDistanceTestCases_SimilarityInput() {
        return withSimilarityInputRepresentations(DamerauLevenshteinDistanceTest::limitedDamerauLevenshteinDistanceTestCases);
    }

    static Stream<Arguments> unlimitedDamerauLevenshteinDistanceTestCases() {
        return Stream.of(
                // Empty input handling.
                Arguments.of("", TEST, 4),
                Arguments.of(TEST, "", 4),

                // Common substitutions, insertions, deletions, and transpositions.
                Arguments.of("kitten", "sitting", 3),
                Arguments.of("saturday", "sunday", 3),
                Arguments.of("hello", "world", 4),
                Arguments.of("algorithm", "logarithm", 3),
                Arguments.of("computer", "comptuer", 1),
                Arguments.of("receive", "recieve", 1),
                Arguments.of("programming", "porgramming", 1),
                Arguments.of(TEST, "tset", 1),
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

                // Longer inputs and repeated-character patterns.
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

    static Stream<Arguments> unlimitedDamerauLevenshteinDistanceTestCases_SimilarityInput() {
        return withSimilarityInputRepresentations(DamerauLevenshteinDistanceTest::unlimitedDamerauLevenshteinDistanceTestCases);
    }

    private static Stream<Arguments> withSimilarityInputRepresentations(final Supplier<Stream<Arguments>> testCases) {
        return SimilarityInputTest.similarityInputs()
                .flatMap(cls -> testCases.get().map(arguments -> appendSimilarityInputClass(arguments, cls)));
    }

    private static Arguments appendSimilarityInputClass(final Arguments arguments, final Class<?> cls) {
        final Object[] values = Arrays.copyOf(arguments.get(), arguments.get().length + 1);
        values[values.length - 1] = cls;
        return Arguments.of(values);
    }

    @ParameterizedTest(name = "DamerauLevenshteinDistance.unlimitedCompare(\"{0}\", \"{1}\") should return {2}")
    @MethodSource("unlimitedDamerauLevenshteinDistanceTestCases")
    void testCalculateDamerauLevenshteinDistance(final String left, final String right, final int expectedDistance) {
        assertSymmetricCharSequenceDistance(defaultInstance, left, right, expectedDistance);
    }

    @ParameterizedTest(name = "DamerauLevenshteinDistance.limitedCompare(\"{0}\", \"{1}\") should return {2}")
    @MethodSource("limitedDamerauLevenshteinDistanceTestCases")
    void testCalculateDamerauLevenshteinDistance(final String left, final String right, final int threshold, final int expectedDistance) {
        final DamerauLevenshteinDistance instance = new DamerauLevenshteinDistance(threshold);
        assertSymmetricCharSequenceDistance(instance, left, right, expectedDistance);
    }

    @ParameterizedTest(name = "DamerauLevenshteinDistance.unlimitedCompare(\"{0}\", \"{1}\") should return {2} ({3})")
    @MethodSource("unlimitedDamerauLevenshteinDistanceTestCases_SimilarityInput")
    void testCalculateDamerauLevenshteinDistance_SimilarityInput(final String left, final String right, final int expectedDistance, final Class<?> cls) {
        assertSymmetricSimilarityInputDistance(defaultInstance, left, right, expectedDistance, cls);
    }

    @ParameterizedTest(name = "DamerauLevenshteinDistance.limitedCompare(\"{0}\", \"{1}\") should return {2}")
    @MethodSource("limitedDamerauLevenshteinDistanceTestCases_SimilarityInput")
    void testCalculateDamerauLevenshteinDistance_SimilarityInput(final String left, final String right, final int threshold, final int expectedDistance,
            final Class<?> cls) {
        final DamerauLevenshteinDistance instance = new DamerauLevenshteinDistance(threshold);
        assertSymmetricSimilarityInputDistance(instance, left, right, expectedDistance, cls);
    }

    private static void assertSymmetricCharSequenceDistance(final DamerauLevenshteinDistance distance, final String left, final String right,
            final int expectedDistance) {
        final int leftRightDistance = distance.apply(left, right);
        final int rightLeftDistance = distance.apply(right, left);
        assertEquals(expectedDistance, leftRightDistance);
        assertEquals(expectedDistance, rightLeftDistance);
    }

    private static void assertSymmetricSimilarityInputDistance(final DamerauLevenshteinDistance distance, final String left, final String right,
            final int expectedDistance, final Class<?> cls) {
        final SimilarityInput<Object> leftInput = SimilarityInputTest.build(cls, left);
        final SimilarityInput<Object> rightInput = SimilarityInputTest.build(cls, right);
        final int leftRightDistance = distance.apply(leftInput, rightInput);
        final int rightLeftDistance = distance.apply(rightInput, leftInput);
        assertEquals(expectedDistance, leftRightDistance);
        assertEquals(expectedDistance, rightLeftDistance);
    }

    @Test
    void testGetThresholdDirectlyAfterObjectInstantiation() {
        assertNull(defaultInstance.getThreshold());
    }

    @Test
    void testGetThresholdIsCorrect() {
        final DamerauLevenshteinDistance distance = new DamerauLevenshteinDistance(POSITIVE_THRESHOLD);
        assertEquals(10, distance.getThreshold());
    }

    @Test
    void testInvalidThresholdThrows() {
        assertThrows(IllegalArgumentException.class, () -> new DamerauLevenshteinDistance(-1));
    }

    @Test
    void testNullInputsThrowLimited() {
        final DamerauLevenshteinDistance instance = new DamerauLevenshteinDistance(POSITIVE_THRESHOLD);
        assertThrows(IllegalArgumentException.class, () -> instance.apply(null, TEST));
        assertThrows(IllegalArgumentException.class, () -> instance.apply(TEST, null));
        assertThrows(IllegalArgumentException.class, () -> instance.apply(null, SimilarityInput.input(TEST)));
        assertThrows(IllegalArgumentException.class, () -> instance.apply(SimilarityInput.input(TEST), null));
    }

    @Test
    void testNullInputsThrowUnlimited() {
        assertThrows(IllegalArgumentException.class, () -> defaultInstance.apply(null, TEST));
        assertThrows(IllegalArgumentException.class, () -> defaultInstance.apply(TEST, null));
        assertThrows(IllegalArgumentException.class, () -> defaultInstance.apply(null, SimilarityInput.input(TEST)));
        assertThrows(IllegalArgumentException.class, () -> defaultInstance.apply(SimilarityInput.input(TEST), null));
    }
}
