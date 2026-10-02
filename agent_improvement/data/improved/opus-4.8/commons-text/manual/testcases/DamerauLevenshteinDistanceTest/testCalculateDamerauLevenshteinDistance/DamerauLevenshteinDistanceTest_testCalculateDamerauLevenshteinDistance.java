package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests {@link DamerauLevenshteinDistance} when configured with a threshold.
 *
 * <p>A threshold-limited instance returns the actual edit distance when it is
 * within (or equal to) the threshold, and {@code -1} when the distance exceeds
 * the threshold. Because Damerau-Levenshtein distance is symmetric, every case
 * is verified in both directions: {@code apply(left, right)} and
 * {@code apply(right, left)} must yield the same result.</p>
 */
public class DamerauLevenshteinDistanceTest_testCalculateDamerauLevenshteinDistance {

    /**
     * Each case is {@code (left, right, threshold, expectedDistance)} where
     * {@code expectedDistance} is {@code -1} when the true distance exceeds the
     * threshold.
     */
    static Stream<Arguments> limitedDamerauLevenshteinDistanceTestCases() {
        return Stream.of(
                // Empty input: distance equals the length of the other string when within threshold...
                Arguments.of("", "test", 10, 4),
                Arguments.of("test", "", 10, 4),
                // ...and -1 once that length exceeds the threshold.
                Arguments.of("", "test", 2, -1),
                Arguments.of("test", "", 2, -1),

                // Distance exceeds the threshold -> -1.
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
                Arguments.of("pneumonoultramicroscopicsilicovolcanoconiosiss",
                        "pneumonoultramicroscopicsilicovolcanoconiosis", 0, -1),

                // Single adjacent transposition counts as one edit (distance 1).
                Arguments.of("computer", "comptuer", 1, 1),
                Arguments.of("test", "tset", 1, 1),
                Arguments.of("information", "infromation", 1, 1),
                Arguments.of("necessary", "neccessary", 1, 1),
                Arguments.of("government", "goverment", 1, 1),
                Arguments.of("thequickbrownfoxjumpsoverthelazydog",
                        "thequickbrownfoxjumpsovrethelazydog", 1, 1),

                // Distance of 1 returned when threshold leaves headroom.
                Arguments.of("receive", "recieve", 3, 1),
                Arguments.of("example", "exmaple", 3, 1),
                Arguments.of("development", "developemnt", 3, 1),
                Arguments.of("separate", "seperate", 1, 1),
                Arguments.of("definitely", "definately", 3, 1),
                Arguments.of("supercalifragilisticexpialidocious",
                        "supercalifragilisticexpialidocous", 3, 1),
                Arguments.of("antidisestablishmentarianism",
                        "antidisestablishmentarianisn", 3, 1),

                // Larger distances returned because they stay within the threshold.
                Arguments.of("saturday", "sunday", 3, 3),
                Arguments.of("hello", "world", 6, 4),
                Arguments.of("restaurant", "restaraunt", 4, 2),
                Arguments.of("abcdefghijklmnop", "ponmlkjihgfedcba", 17, 15),
                Arguments.of("abababababab", "babababababa", 2, 2),
                Arguments.of("abcdefg", "gfedcba", 6, 6),
                Arguments.of("xyxyxyxyxy", "yxyxyxyxyx", 4, 2));
    }

    @ParameterizedTest(name = "DamerauLevenshteinDistance(threshold={2}).apply(\"{0}\", \"{1}\") should return {3}")
    @MethodSource("limitedDamerauLevenshteinDistanceTestCases")
    void testCalculateDamerauLevenshteinDistance(final String left, final String right,
            final int threshold, final int expectedDistance) {
        final DamerauLevenshteinDistance instance = new DamerauLevenshteinDistance(threshold);

        // The distance is symmetric, so it must be the same in both directions.
        assertEquals(expectedDistance, instance.apply(left, right));
        assertEquals(expectedDistance, instance.apply(right, left));
    }
}
