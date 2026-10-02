package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class LevenshteinDetailedDistanceTest_testCreatesLevenshteinDetailedDistanceTakingInteger6 {

    // With threshold=0, an empty left string compared to any non-empty right string
    // exceeds the threshold, so the result distance is -1 and all operation counts are 0.
    @ParameterizedTest
    @MethodSource("org.apache.commons.text.similarity.SimilarityInputTest#similarityInputs()")
    void testCreatesLevenshteinDetailedDistanceTakingInteger6(final Class<?> cls) {
        final LevenshteinDetailedDistance distanceWithZeroThreshold = new LevenshteinDetailedDistance(0);

        final String emptyLeft = "";
        final String nonEmptyRight = "Distance: 38, Insert: 0, Delete: 0, Substitute: 0";

        final LevenshteinResults results = distanceWithZeroThreshold.apply(emptyLeft, nonEmptyRight);

        // Threshold exceeded: distance is -1 and no edit operations are counted
        assertEquals(-1, results.getDistance());
        assertEquals(0, results.getInsertCount());
        assertEquals(0, results.getDeleteCount());
        assertEquals(0, results.getSubstituteCount());

        // Same result must be returned when inputs are wrapped as SimilarityInput
        assertEquals(results, distanceWithZeroThreshold.apply(
                SimilarityInputTest.build(cls, emptyLeft),
                SimilarityInputTest.build(cls, nonEmptyRight)));
    }
}
