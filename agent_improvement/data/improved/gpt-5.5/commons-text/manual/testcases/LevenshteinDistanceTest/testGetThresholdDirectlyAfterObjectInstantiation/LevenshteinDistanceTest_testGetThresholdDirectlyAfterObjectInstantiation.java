package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class LevenshteinDistanceTest_testGetThresholdDirectlyAfterObjectInstantiation {

    private static final LevenshteinDistance DEFAULT_DISTANCE = LevenshteinDistance.getDefaultInstance();

    @Test
    void testGetThresholdDirectlyAfterObjectInstantiation() {
        final LevenshteinDistance defaultDistance = LevenshteinDistance.getDefaultInstance();

        assertNull(defaultDistance.getThreshold());
    }
}
