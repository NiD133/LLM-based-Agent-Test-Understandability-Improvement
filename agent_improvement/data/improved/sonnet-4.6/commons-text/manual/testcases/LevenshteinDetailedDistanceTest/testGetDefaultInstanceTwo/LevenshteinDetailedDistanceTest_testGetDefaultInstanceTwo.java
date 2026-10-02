package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class LevenshteinDetailedDistanceTest_testGetDefaultInstanceTwo {

    private static final LevenshteinDetailedDistance UNLIMITED_DISTANCE = LevenshteinDetailedDistance.getDefaultInstance();

    // Two strings that differ at two numeric fields (Integer.MAX_VALUE vs 0), yielding an edit distance of 20.
    private static final String LEFT_INPUT  = "Distance: 2147483647, Insert: 0, Delete: 0, Substitute: 0";
    private static final String RIGHT_INPUT = "Distance: 0, Insert: 2147483647, Delete: 0, Substitute: 0";

    private static final int EXPECTED_DISTANCE = 20;

    @ParameterizedTest
    @MethodSource("org.apache.commons.text.similarity.SimilarityInputTest#similarityInputs()")
    void testGetDefaultInstanceTwo(final Class<?> cls) {
        LevenshteinResults resultFromCharSequences = UNLIMITED_DISTANCE.apply(LEFT_INPUT, RIGHT_INPUT);

        assertEquals(EXPECTED_DISTANCE, resultFromCharSequences.getDistance());

        LevenshteinResults resultFromSimilarityInputs = UNLIMITED_DISTANCE.apply(
            SimilarityInputTest.build(cls, LEFT_INPUT),
            SimilarityInputTest.build(cls, RIGHT_INPUT)
        );

        assertEquals(resultFromCharSequences, resultFromSimilarityInputs);
    }
}
