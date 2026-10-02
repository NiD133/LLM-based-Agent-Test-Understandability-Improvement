package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class LevenshteinDetailedDistanceTest_testGetThreshold {

    @Test
    void testGetThreshold() {
        final LevenshteinDetailedDistance distance = new LevenshteinDetailedDistance(0);

        assertEquals(0, distance.getThreshold());
    }
}
