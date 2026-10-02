package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

/**
 * Verifies the threshold of a {@link DamerauLevenshteinDistance} created with its
 * no-argument constructor.
 */
public class DamerauLevenshteinDistanceTest_testGetThresholdDirectlyAfterObjectInstantiation {

    /**
     * The default constructor selects the unlimited algorithm, so the instance must
     * report no threshold.
     */
    @Test
    void testGetThresholdDirectlyAfterObjectInstantiation() {
        final DamerauLevenshteinDistance defaultInstance = new DamerauLevenshteinDistance();

        assertNull(defaultInstance.getThreshold());
    }
}
