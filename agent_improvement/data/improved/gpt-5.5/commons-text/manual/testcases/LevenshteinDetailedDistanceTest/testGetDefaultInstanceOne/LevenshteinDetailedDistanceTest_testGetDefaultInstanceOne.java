package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class LevenshteinDetailedDistanceTest_testGetDefaultInstanceOne {

    private static final String LEFT_TEXT = "Distance: -2147483643, Insert: 0, Delete: 0, Substitute: 0";
    private static final String RIGHT_TEXT = "Distance: 0, Insert: 2147483536, Delete: 0, Substitute: 0";
    private static final int EXPECTED_DISTANCE = 21;

    @ParameterizedTest
    @MethodSource("org.apache.commons.text.similarity.SimilarityInputTest#similarityInputs()")
    void testGetDefaultInstanceOne(final Class<?> cls) {
        final LevenshteinDetailedDistance distance = LevenshteinDetailedDistance.getDefaultInstance();

        final LevenshteinResults results = distance.apply(
                SimilarityInputTest.build(cls, LEFT_TEXT),
                SimilarityInputTest.build(cls, RIGHT_TEXT));

        assertEquals(EXPECTED_DISTANCE, results.getDistance());
    }
}
