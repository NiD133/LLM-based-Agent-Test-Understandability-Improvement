package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

public class LevenshteinDetailedDistanceTest_testApplyWithNullString {

    // A threshold-based instance (threshold=0) is used here because the null-check
    // applies equally to both the limited and unlimited algorithm paths.
    private static final LevenshteinDetailedDistance THRESHOLD_ZERO_DISTANCE =
            new LevenshteinDetailedDistance(0);

    @Test
    void testApplyWithNullString() {
        // Both null left and null right arguments must trigger IllegalArgumentException.
        assertThrows(IllegalArgumentException.class,
                () -> THRESHOLD_ZERO_DISTANCE.apply((String) null, (String) null));
    }
}
