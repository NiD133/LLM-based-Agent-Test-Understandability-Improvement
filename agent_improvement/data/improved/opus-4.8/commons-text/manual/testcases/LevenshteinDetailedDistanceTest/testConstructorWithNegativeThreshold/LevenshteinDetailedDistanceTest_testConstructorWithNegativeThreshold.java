package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link LevenshteinDetailedDistance} rejects a negative threshold.
 */
public class LevenshteinDetailedDistanceTest_testConstructorWithNegativeThreshold {

    /**
     * The constructor documents that the threshold "may not be negative",
     * so constructing with a negative value must raise an
     * {@link IllegalArgumentException}.
     */
    @Test
    void testConstructorWithNegativeThreshold() {
        final int negativeThreshold = -1;

        assertThrows(IllegalArgumentException.class,
                () -> new LevenshteinDetailedDistance(negativeThreshold));
    }
}
