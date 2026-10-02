package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class DamerauLevenshteinDistanceTest_testGetThresholdDirectlyAfterObjectInstantiation {

    private static DamerauLevenshteinDistance defaultDistance;

    @BeforeAll
    static void createDefaultDistance() {
        defaultDistance = new DamerauLevenshteinDistance();
    }

    @Test
    void testGetThresholdDirectlyAfterObjectInstantiation() {
        assertNull(defaultDistance.getThreshold());
    }
}
