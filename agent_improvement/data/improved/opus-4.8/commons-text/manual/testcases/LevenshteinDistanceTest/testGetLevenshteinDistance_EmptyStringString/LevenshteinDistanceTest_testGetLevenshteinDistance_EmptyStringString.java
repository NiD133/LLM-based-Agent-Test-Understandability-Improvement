package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Verifies how {@link LevenshteinDistance} behaves when one input is empty and the
 * configured threshold is too small to allow any edits.
 */
public class LevenshteinDistanceTest_testGetLevenshteinDistance_EmptyStringString {

    @Test
    void testGetLevenshteinDistance_EmptyStringString() {
        // A threshold of 0 means "report the distance only if it is 0, otherwise give up".
        final LevenshteinDistance zeroThresholdDistance = new LevenshteinDistance(0);

        // Turning the empty string into "asdf" requires 4 insertions, which exceeds the
        // threshold of 0, so the algorithm cannot satisfy the limit.
        final SimilarityCharacterInput emptyInput = new SimilarityCharacterInput("");
        final SimilarityCharacterInput fourCharacterInput = new SimilarityCharacterInput("asdf");

        final int distance = zeroThresholdDistance.apply(emptyInput, fourCharacterInput);

        // -1 is the sentinel value signalling that the real distance is above the threshold.
        final int distanceExceedsThreshold = -1;
        assertEquals(distanceExceedsThreshold, distance,
                "Distance from \"\" to \"asdf\" exceeds the threshold of 0, so -1 is expected");
    }
}
