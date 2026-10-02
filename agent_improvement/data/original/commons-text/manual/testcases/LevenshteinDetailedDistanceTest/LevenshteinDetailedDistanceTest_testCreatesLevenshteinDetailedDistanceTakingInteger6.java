package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.apache.commons.text.TextStringBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class LevenshteinDetailedDistanceTest_testCreatesLevenshteinDetailedDistanceTakingInteger6 {

    private static final LevenshteinDetailedDistance UNLIMITED_DISTANCE = LevenshteinDetailedDistance.getDefaultInstance();

    @ParameterizedTest
    @MethodSource("org.apache.commons.text.similarity.SimilarityInputTest#similarityInputs()")
    void testCreatesLevenshteinDetailedDistanceTakingInteger6(final Class<?> cls) {
        final LevenshteinDetailedDistance levenshteinDetailedDistance = new LevenshteinDetailedDistance(0);
        final LevenshteinResults levenshteinResults = levenshteinDetailedDistance.apply("", "Distance: 38, Insert: 0, Delete: 0, Substitute: 0");
        assertEquals(0, levenshteinResults.getSubstituteCount());
        assertEquals(0, levenshteinResults.getDeleteCount());
        assertEquals(0, levenshteinResults.getInsertCount());
        assertEquals(-1, levenshteinResults.getDistance());
        assertEquals(levenshteinResults, levenshteinDetailedDistance.apply(SimilarityInputTest.build(cls, ""), SimilarityInputTest.build(cls, "Distance: 38, Insert: 0, Delete: 0, Substitute: 0")));
    }
}
