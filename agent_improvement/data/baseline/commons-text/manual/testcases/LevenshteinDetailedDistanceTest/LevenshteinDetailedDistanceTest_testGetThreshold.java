package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.apache.commons.text.TextStringBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class LevenshteinDetailedDistanceTest_testGetThreshold {

    private static final LevenshteinDetailedDistance UNLIMITED_DISTANCE = LevenshteinDetailedDistance.getDefaultInstance();

    @Test
    void testGetThreshold() {
        final LevenshteinDetailedDistance levenshteinDetailedDistance = new LevenshteinDetailedDistance(0);
        assertEquals(0, levenshteinDetailedDistance.getThreshold());
    }
}
