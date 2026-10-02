package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class DamerauLevenshteinDistanceTest_testGetThresholdDirectlyAfterObjectInstantiation {

    @Test
    void testGetThresholdDirectlyAfterObjectInstantiation() {
        // The no-arg constructor creates an unlimited instance (threshold == null)
        DamerauLevenshteinDistance defaultInstance = new DamerauLevenshteinDistance();
        assertNull(defaultInstance.getThreshold());
    }
}
