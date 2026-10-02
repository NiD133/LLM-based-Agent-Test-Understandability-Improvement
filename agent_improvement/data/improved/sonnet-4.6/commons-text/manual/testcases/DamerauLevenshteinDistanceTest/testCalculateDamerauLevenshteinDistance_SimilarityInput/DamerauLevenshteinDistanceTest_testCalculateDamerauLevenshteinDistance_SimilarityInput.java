package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Arrays;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class DamerauLevenshteinDistanceTest_testCalculateDamerauLevenshteinDistance_SimilarityInput {

    /**
     * Test cases for {@link DamerauLevenshteinDistance#apply(SimilarityInput, SimilarityInput)}
     * with a threshold.
     *
     * Each entry is: (left, right, threshold, expectedDistance).
     * An expectedDistance of -1 means the true distance exceeds the threshold.
     */
    static Stream<Arguments> limitedDamerauLevenshteinDistanceTestCases() {
        return Stream.of(
            // Empty-string edge cases: distance equals the length of the other string
            Arguments.of("", "test", 10, 4),
            Arguments.of("test", "", 10, 4),

            // Empty-string edge cases where true distance exceeds threshold -> -1
            Arguments.of("", "test", 2, -1),
            Arguments.of("test", "", 2, -1),

            // Length difference alone exceeds threshold -> -1
            Arguments.of("testing long string", "testing", 2, -1),

            // Many edits required, threshold too low -> -1
            Arguments.of("kitten", "sitting", 1, -1),

            // Moderate distance within threshold
            Arguments.of("saturday", "sunday", 3, 3),
            Arguments.of("hello", "world", 6, 4),

            // Distance exceeds threshold -> -1
            Arguments.of("algorithm", "logarithm", 1, -1),

            // Single adjacent-character transposition (1 Damerau edit)
            Arguments.of("computer", "comptuer", 1, 1),
            Arguments.of("receive", "recieve", 3, 1),

            // Threshold of 0 with a transposition -> -1 (distance = 1 > 0)
            Arguments.of("programming", "porgramming", 0, -1),

            // Single transposition within threshold
            Arguments.of("test", "tset", 1, 1),
            Arguments.of("example", "exmaple", 3, 1),

            // Transposition with threshold 0 -> -1
            Arguments.of("transform", "transfrom", 0, -1),

            // Single transposition within threshold (common misspellings)
            Arguments.of("information", "infromation", 1, 1),
            Arguments.of("development", "developemnt", 3, 1),

            // Transposition with threshold 0 -> -1
            Arguments.of("password", "passwrod", 0, -1),

            // Single edit within threshold (common misspellings)
            Arguments.of("separate", "seperate", 1, 1),
            Arguments.of("definitely", "definately", 3, 1),

            // Threshold 0 -> -1 (distance = 1)
            Arguments.of("occurrence", "occurence", 0, -1),

            // Single insertion within threshold
            Arguments.of("necessary", "neccessary", 1, 1),

            // Two edits within threshold
            Arguments.of("restaurant", "restaraunt", 4, 2),

            // Threshold 0 -> -1 (distance = 1)
            Arguments.of("beginning", "begining", 0, -1),

            // Single deletion within threshold
            Arguments.of("government", "goverment", 1, 1),

            // Long strings requiring many edits
            Arguments.of("abcdefghijklmnop", "ponmlkjihgfedcba", 17, 15),

            // Completely different characters, distance equals full length -> exceeds threshold
            Arguments.of("AAAAAAAAAA", "BBBBBBBBBB", 5, -1),

            // Alternating patterns: only 2 transpositions needed
            Arguments.of("abababababab", "babababababa", 2, 2),

            // Long word with single deletion within threshold
            Arguments.of("supercalifragilisticexpialidocious", "supercalifragilisticexpialidocous", 3, 1),

            // Extra trailing character; threshold 0 -> -1
            Arguments.of("pneumonoultramicroscopicsilicovolcanoconiosiss", "pneumonoultramicroscopicsilicovolcanoconiosis", 0, -1),

            // Full reversal
            Arguments.of("abcdefg", "gfedcba", 6, 6),

            // Cyclic shift: 2 transpositions within threshold
            Arguments.of("xyxyxyxyxy", "yxyxyxyxyx", 4, 2),

            // Complete reversal of character blocks; exceeds threshold
            Arguments.of("aaaaabbbbbccccc", "cccccbbbbbaaaaa", 5, -1),

            // Single transposition in a long string within threshold
            Arguments.of("thequickbrownfoxjumpsoverthelazydog", "thequickbrownfoxjumpsovrethelazydog", 1, 1),
            Arguments.of("antidisestablishmentarianism", "antidisestablishmentarianisn", 3, 1)
        );
    }

    static Stream<Arguments> limitedDamerauLevenshteinDistanceTestCases_SimilarityInput() {
        return SimilarityInputTest.similarityInputs().flatMap(cls ->
            limitedDamerauLevenshteinDistanceTestCases().map(arguments -> {
                final Object[] values = Arrays.copyOf(arguments.get(), arguments.get().length + 1);
                values[values.length - 1] = cls;
                return Arguments.of(values);
            })
        );
    }

    /**
     * Verifies that the distance is symmetric (left-to-right equals right-to-left)
     * and matches the expected value for each (left, right, threshold) combination,
     * exercising all {@link SimilarityInput} implementations.
     *
     * <p>expectedDistance is -1 when the true distance exceeds the threshold.</p>
     */
    @ParameterizedTest(name = "[{5}] limitedCompare(\"{0}\", \"{1}\", threshold={2}) == {3}")
    @MethodSource("limitedDamerauLevenshteinDistanceTestCases_SimilarityInput")
    void testCalculateDamerauLevenshteinDistance_SimilarityInput(
            final String left,
            final String right,
            final int threshold,
            final int expectedDistance,
            final Class<?> cls) {
        final DamerauLevenshteinDistance instance = new DamerauLevenshteinDistance(threshold);
        final SimilarityInput<Object> leftInput = SimilarityInputTest.build(cls, left);
        final SimilarityInput<Object> rightInput = SimilarityInputTest.build(cls, right);

        final int leftRightDistance = instance.apply(leftInput, rightInput);
        final int rightLeftDistance = instance.apply(rightInput, leftInput);

        assertEquals(expectedDistance, leftRightDistance,
            () -> String.format("apply(\"%s\", \"%s\") with threshold=%d", left, right, threshold));
        assertEquals(expectedDistance, rightLeftDistance,
            () -> String.format("apply(\"%s\", \"%s\") with threshold=%d (symmetric)", right, left, threshold));
    }
}
