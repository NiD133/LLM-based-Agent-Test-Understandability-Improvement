package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class LevenshteinDetailedDistanceTest_testCreatesLevenshteinDetailedDistanceTakingInteger6 {

    private static final int EXACT_MATCH_THRESHOLD = 0;
    private static final String EMPTY_INPUT = "";
    private static final String RIGHT_INPUT = "Distance: 38, Insert: 0, Delete: 0, Substitute: 0";

    @ParameterizedTest
    @MethodSource("org.apache.commons.text.similarity.SimilarityInputTest#similarityInputs()")
    void testCreatesLevenshteinDetailedDistanceTakingInteger6(final Class<?> cls) {
        final LevenshteinDetailedDistance distance = new LevenshteinDetailedDistance(EXACT_MATCH_THRESHOLD);

        final LevenshteinResults stringResult = distance.apply(EMPTY_INPUT, RIGHT_INPUT);

        assertBeyondThresholdResult(stringResult);
        assertEquals(stringResult, distance.apply(
                SimilarityInputTest.build(cls, EMPTY_INPUT),
                SimilarityInputTest.build(cls, RIGHT_INPUT)));
    }

    private static void assertBeyondThresholdResult(final LevenshteinResults result) {
        assertEquals(0, result.getSubstituteCount());
        assertEquals(0, result.getDeleteCount());
        assertEquals(0, result.getInsertCount());
        assertEquals(-1, result.getDistance());
    }
}
