package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class DamerauLevenshteinDistanceTest_testCalculateDamerauLevenshteinDistance_SimilarityInput {

    private static Arguments limitedDistanceCase(final String left, final String right, final int threshold,
            final int expectedDistance) {
        return Arguments.of(left, right, threshold, expectedDistance);
    }

    static Stream<Arguments> limitedDamerauLevenshteinDistanceTestCases() {
        return Stream.of(
                limitedDistanceCase("", "test", 10, 4),
                limitedDistanceCase("test", "", 10, 4),
                limitedDistanceCase("", "test", 2, -1),
                limitedDistanceCase("test", "", 2, -1),
                limitedDistanceCase("testing long string", "testing", 2, -1),
                limitedDistanceCase("kitten", "sitting", 1, -1),
                limitedDistanceCase("saturday", "sunday", 3, 3),
                limitedDistanceCase("hello", "world", 6, 4),
                limitedDistanceCase("algorithm", "logarithm", 1, -1),
                limitedDistanceCase("computer", "comptuer", 1, 1),
                limitedDistanceCase("receive", "recieve", 3, 1),
                limitedDistanceCase("programming", "porgramming", 0, -1),
                limitedDistanceCase("test", "tset", 1, 1),
                limitedDistanceCase("example", "exmaple", 3, 1),
                limitedDistanceCase("transform", "transfrom", 0, -1),
                limitedDistanceCase("information", "infromation", 1, 1),
                limitedDistanceCase("development", "developemnt", 3, 1),
                limitedDistanceCase("password", "passwrod", 0, -1),
                limitedDistanceCase("separate", "seperate", 1, 1),
                limitedDistanceCase("definitely", "definately", 3, 1),
                limitedDistanceCase("occurrence", "occurence", 0, -1),
                limitedDistanceCase("necessary", "neccessary", 1, 1),
                limitedDistanceCase("restaurant", "restaraunt", 4, 2),
                limitedDistanceCase("beginning", "begining", 0, -1),
                limitedDistanceCase("government", "goverment", 1, 1),
                limitedDistanceCase("abcdefghijklmnop", "ponmlkjihgfedcba", 17, 15),
                limitedDistanceCase("AAAAAAAAAA", "BBBBBBBBBB", 5, -1),
                limitedDistanceCase("abababababab", "babababababa", 2, 2),
                limitedDistanceCase("supercalifragilisticexpialidocious", "supercalifragilisticexpialidocous", 3, 1),
                limitedDistanceCase("pneumonoultramicroscopicsilicovolcanoconiosiss",
                        "pneumonoultramicroscopicsilicovolcanoconiosis", 0, -1),
                limitedDistanceCase("abcdefg", "gfedcba", 6, 6),
                limitedDistanceCase("xyxyxyxyxy", "yxyxyxyxyx", 4, 2),
                limitedDistanceCase("aaaaabbbbbccccc", "cccccbbbbbaaaaa", 5, -1),
                limitedDistanceCase("thequickbrownfoxjumpsoverthelazydog",
                        "thequickbrownfoxjumpsovrethelazydog", 1, 1),
                limitedDistanceCase("antidisestablishmentarianism", "antidisestablishmentarianisn", 3, 1));
    }

    static Stream<Arguments> limitedDamerauLevenshteinDistanceTestCases_SimilarityInput() {
        return SimilarityInputTest.similarityInputs()
                .flatMap(inputType -> limitedDamerauLevenshteinDistanceTestCases()
                        .map(arguments -> appendSimilarityInputType(arguments, inputType)));
    }

    private static Arguments appendSimilarityInputType(final Arguments arguments, final Class<?> inputType) {
        final Object[] values = arguments.get();
        final Object[] valuesWithInputType = Arrays.copyOf(values, values.length + 1);
        valuesWithInputType[valuesWithInputType.length - 1] = inputType;
        return Arguments.of(valuesWithInputType);
    }

    @ParameterizedTest(name = "DamerauLevenshteinDistance({2}).apply(\"{0}\", \"{1}\") should return {3}")
    @MethodSource("limitedDamerauLevenshteinDistanceTestCases_SimilarityInput")
    void testCalculateDamerauLevenshteinDistance_SimilarityInput(final String left, final String right,
            final int threshold, final int expectedDistance, final Class<?> inputType) {
        final DamerauLevenshteinDistance instance = new DamerauLevenshteinDistance(threshold);
        final SimilarityInput<Object> leftInput = SimilarityInputTest.build(inputType, left);
        final SimilarityInput<Object> rightInput = SimilarityInputTest.build(inputType, right);

        final int leftRightDistance = instance.apply(leftInput, rightInput);
        final int rightLeftDistance = instance.apply(rightInput, leftInput);

        assertEquals(expectedDistance, leftRightDistance);
        assertEquals(expectedDistance, rightLeftDistance);
    }
}
