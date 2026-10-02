package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

/**
 * Verifies the threshold state of a freshly obtained default
 * {@link LevenshteinDistance} instance.
 */
public class LevenshteinDistanceTest_testGetThresholdDirectlyAfterObjectInstantiation {

    /**
     * The default instance is created without a distance limit, so its
     * threshold should be {@code null} right after it is obtained.
     */
    @Test
    void thresholdOfDefaultInstanceIsNull() {
        final LevenshteinDistance defaultInstance = LevenshteinDistance.getDefaultInstance();

        assertNull(defaultInstance.getThreshold());
    }
}
