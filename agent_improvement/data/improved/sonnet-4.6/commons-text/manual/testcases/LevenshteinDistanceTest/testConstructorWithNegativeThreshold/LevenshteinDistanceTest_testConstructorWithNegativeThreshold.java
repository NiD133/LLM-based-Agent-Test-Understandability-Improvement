package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link LevenshteinDistance} rejects negative threshold values
 * at construction time with an {@link IllegalArgumentException}.
 */
public class LevenshteinDistanceTest_testConstructorWithNegativeThreshold {

    /** A threshold value that is below the minimum allowed value of zero. */
    private static final int NEGATIVE_THRESHOLD = -1;

    @Test
    void testConstructorWithNegativeThreshold() {
        // The constructor contract requires threshold >= 0; a negative value must be rejected immediately.
        assertThrows(IllegalArgumentException.class,
                () -> new LevenshteinDistance(NEGATIVE_THRESHOLD));
    }
}
