package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class LevenshteinDetailedDistanceTest_testGetDefaultInstanceTwo {

    private static final String LEFT_TEXT = "Distance: 2147483647, Insert: 0, Delete: 0, Substitute: 0";
    private static final String RIGHT_TEXT = "Distance: 0, Insert: 2147483647, Delete: 0, Substitute: 0";
    private static final int EXPECTED_DISTANCE = 20;

    @ParameterizedTest
    @MethodSource("org.apache.commons.text.similarity.SimilarityInputTest#similarityInputs()")
    void testGetDefaultInstanceTwo(final Class<?> cls) {
        final LevenshteinDetailedDistance distance = LevenshteinDetailedDistance.getDefaultInstance();

        final LevenshteinResults stringResults = distance.apply(LEFT_TEXT, RIGHT_TEXT);

        assertEquals(EXPECTED_DISTANCE, stringResults.getDistance());
        assertEquals(
                stringResults,
                distance.apply(SimilarityInputTest.build(cls, LEFT_TEXT), SimilarityInputTest.build(cls, RIGHT_TEXT)));
    }
}
