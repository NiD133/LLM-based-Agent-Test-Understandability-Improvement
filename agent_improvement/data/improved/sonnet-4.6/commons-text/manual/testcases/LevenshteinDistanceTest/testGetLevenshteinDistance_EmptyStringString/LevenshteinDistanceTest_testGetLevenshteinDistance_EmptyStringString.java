package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class LevenshteinDistanceTest_testGetLevenshteinDistance_EmptyStringString {

    // When the edit distance exceeds the threshold, the algorithm returns -1.
    private static final int RESULT_EXCEEDS_THRESHOLD = -1;

    @Test
    void testGetLevenshteinDistance_EmptyStringString() {
        // With threshold=0, converting "" to "asdf" requires 4 edits (> 0), so -1 is returned.
        LevenshteinDistance distanceWithThresholdZero = new LevenshteinDistance(0);
        SimilarityInput<Character> emptyInput = new SimilarityCharacterInput("");
        SimilarityInput<Character> targetInput = new SimilarityCharacterInput("asdf");

        int result = distanceWithThresholdZero.apply(emptyInput, targetInput);

        assertEquals(RESULT_EXCEEDS_THRESHOLD, result);
    }
}
