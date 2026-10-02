package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class DamerauLevenshteinDistanceTest_testNullInputsThrowLimited {

    private static final int THRESHOLD = 10;

    /**
     * Verifies that a threshold-limited instance rejects null on either side of apply(),
     * for both the CharSequence overload and the SimilarityInput overload.
     */
    @Test
    void testNullInputsThrowLimited() {
        final DamerauLevenshteinDistance instance = new DamerauLevenshteinDistance(THRESHOLD);

        // CharSequence overload: null left argument
        assertThrows(IllegalArgumentException.class, () -> instance.apply(null, "test"));
        // CharSequence overload: null right argument
        assertThrows(IllegalArgumentException.class, () -> instance.apply("test", null));
        // SimilarityInput overload: null left argument
        assertThrows(IllegalArgumentException.class, () -> instance.apply(null, SimilarityInput.input("test")));
        // SimilarityInput overload: null right argument
        assertThrows(IllegalArgumentException.class, () -> instance.apply(SimilarityInput.input("test"), null));
    }
}
