package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class DamerauLevenshteinDistanceTest_testCalculateDamerauLevenshteinDistance {

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
                Arguments.of("pneumonoultramicroscopicsilicovolcanoconiosiss",
                        "pneumonoultramicroscopicsilicovolcanoconiosis", 0, -1),
                Arguments.of("abcdefg", "gfedcba", 6, 6),
                Arguments.of("xyxyxyxyxy", "yxyxyxyxyx", 4, 2),
                Arguments.of("aaaaabbbbbccccc", "cccccbbbbbaaaaa", 5, -1),
                Arguments.of("thequickbrownfoxjumpsoverthelazydog", "thequickbrownfoxjumpsovrethelazydog", 1, 1),
                Arguments.of("antidisestablishmentarianism", "antidisestablishmentarianisn", 3, 1));
    }

    @ParameterizedTest(name = "DamerauLevenshteinDistance.limitedCompare(\"{0}\", \"{1}\") should return {2}")
    @MethodSource("limitedDamerauLevenshteinDistanceTestCases")
    void testCalculateDamerauLevenshteinDistance(final String left, final String right, final int threshold,
            final int expectedDistance) {
        final DamerauLevenshteinDistance instance = new DamerauLevenshteinDistance(threshold);
        final int leftRightDistance = instance.apply(left, right);
        final int rightLeftDistance = instance.apply(right, left);

        assertEquals(expectedDistance, leftRightDistance);
        assertEquals(expectedDistance, rightLeftDistance);
    }
}
