package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Verifies that a threshold-limited {@link LevenshteinDetailedDistance} returns a
 * "distance not computed" result when the true distance would exceed the threshold,
 * and that this behaviour is identical for plain {@code CharSequence} inputs and for
 * equivalent {@link SimilarityInput} wrappers.
 */
public class LevenshteinDetailedDistanceTest_testCreatesLevenshteinDetailedDistanceTakingInteger6 {

    /** Threshold of 0 means: only an exact match (distance 0) is reported; anything larger yields -1. */
    private static final int ZERO_THRESHOLD = 0;

    /** Sentinel result used when the distance exceeds the threshold and is therefore not computed. */
    private static final int DISTANCE_NOT_COMPUTED = -1;

    /** Empty source string; transforming it into any non-empty string costs at least one edit. */
    private static final String EMPTY = "";

    /** Non-empty target string; its only relevant property here is that it is longer than the threshold. */
    private static final String NON_EMPTY_TARGET = "Distance: 38, Insert: 0, Delete: 0, Substitute: 0";

    /**
     * With a threshold of 0, converting "" into a non-empty string would require more edits than
     * the threshold allows, so the detailed distance is not computed: the distance is reported as
     * -1 and all operation counts (insert/delete/substitute) are 0.
     *
     * <p>The same outcome must hold whether the inputs are passed as raw {@code CharSequence}s or
     * wrapped in the {@link SimilarityInput} implementation provided by the parameter {@code cls}.</p>
     */
    @ParameterizedTest
    @MethodSource("org.apache.commons.text.similarity.SimilarityInputTest#similarityInputs()")
    void thresholdZeroReportsDistanceNotComputedForAnyInputType(final Class<?> cls) {
        final LevenshteinDetailedDistance thresholdZeroDistance = new LevenshteinDetailedDistance(ZERO_THRESHOLD);

        final LevenshteinResults resultForStrings = thresholdZeroDistance.apply(EMPTY, NON_EMPTY_TARGET);

        assertEquals(0, resultForStrings.getInsertCount());
        assertEquals(0, resultForStrings.getDeleteCount());
        assertEquals(0, resultForStrings.getSubstituteCount());
        assertEquals(DISTANCE_NOT_COMPUTED, resultForStrings.getDistance());

        final LevenshteinResults resultForSimilarityInputs = thresholdZeroDistance.apply(
                SimilarityInputTest.build(cls, EMPTY),
                SimilarityInputTest.build(cls, NON_EMPTY_TARGET));

        assertEquals(resultForStrings, resultForSimilarityInputs);
    }
}
