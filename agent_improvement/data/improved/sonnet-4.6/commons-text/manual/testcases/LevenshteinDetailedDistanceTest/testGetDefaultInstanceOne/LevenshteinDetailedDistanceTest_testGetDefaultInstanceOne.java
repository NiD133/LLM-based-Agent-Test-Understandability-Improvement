package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.apache.commons.text.TextStringBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class LevenshteinDetailedDistanceTest_testGetDefaultInstanceOne {

    private static final LevenshteinDetailedDistance UNLIMITED_DISTANCE = LevenshteinDetailedDistance.getDefaultInstance();

    // Two strings that differ in exactly 21 characters (the numeric values differ: -2147483643 vs 0, and 0 vs 2147483536)
    private static final String LEFT_INPUT  = "Distance: -2147483643, Insert: 0, Delete: 0, Substitute: 0";
    private static final String RIGHT_INPUT = "Distance: 0, Insert: 2147483536, Delete: 0, Substitute: 0";

    private static final int EXPECTED_DISTANCE = 21;

    @ParameterizedTest
    @MethodSource("org.apache.commons.text.similarity.SimilarityInputTest#similarityInputs()")
    void testGetDefaultInstanceOne(final Class<?> cls) {
        final LevenshteinResults result = UNLIMITED_DISTANCE.apply(
                SimilarityInputTest.build(cls, LEFT_INPUT),
                SimilarityInputTest.build(cls, RIGHT_INPUT));

        assertEquals(EXPECTED_DISTANCE, result.getDistance());
    }
}
