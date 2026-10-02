package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class DamerauLevenshteinDistanceTest_testInvalidThresholdThrows {

    /**
     * The constructor rejects a negative threshold by throwing
     * {@link IllegalArgumentException} (see {@code DamerauLevenshteinDistance(Integer)},
     * which guards against {@code threshold < 0}).
     */
    @Test
    void testInvalidThresholdThrows() {
        final int negativeThreshold = -1;
        assertThrows(IllegalArgumentException.class,
                () -> new DamerauLevenshteinDistance(negativeThreshold));
    }
}
