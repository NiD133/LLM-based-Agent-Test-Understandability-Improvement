package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

public class LevenshteinDistanceTest_testGetThresholdDirectlyAfterObjectInstantiation {

    // The default (unlimited) instance is constructed without a threshold, so getThreshold() must return null.
    private static final LevenshteinDistance UNLIMITED_DISTANCE = LevenshteinDistance.getDefaultInstance();

    @Test
    void testGetThresholdDirectlyAfterObjectInstantiation() {
        assertNull(UNLIMITED_DISTANCE.getThreshold());
    }
}
