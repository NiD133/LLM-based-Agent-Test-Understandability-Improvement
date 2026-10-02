package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Tests that the {@link LevenshteinDistance} constructor rejects a negative threshold.
 */
public class LevenshteinDistanceTest_testConstructorWithNegativeThreshold {

    /**
     * The threshold limits how far the distance algorithm searches. A negative value is
     * meaningless, so the constructor must reject it with an {@link IllegalArgumentException}.
     */
    @Test
    void testConstructorWithNegativeThreshold() {
        final int negativeThreshold = -1;

        assertThrows(IllegalArgumentException.class,
                () -> new LevenshteinDistance(negativeThreshold));
    }
}
