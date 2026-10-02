package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class LevenshteinDistanceTest_testGetLevenshteinDistance_EmptyStringString {

    @Test
    void testGetLevenshteinDistance_EmptyStringString() {
        final LevenshteinDistance zeroThresholdDistance = new LevenshteinDistance(0);
        final SimilarityCharacterInput emptyInput = new SimilarityCharacterInput("");
        final SimilarityCharacterInput nonEmptyInput = new SimilarityCharacterInput("asdf");

        assertEquals(-1, zeroThresholdDistance.apply(emptyInput, nonEmptyInput));
    }
}
