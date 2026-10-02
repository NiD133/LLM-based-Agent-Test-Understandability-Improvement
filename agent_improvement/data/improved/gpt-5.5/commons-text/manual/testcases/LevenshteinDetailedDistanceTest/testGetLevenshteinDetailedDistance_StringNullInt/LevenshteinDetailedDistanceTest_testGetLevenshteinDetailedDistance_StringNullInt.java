package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class LevenshteinDetailedDistanceTest_testGetLevenshteinDetailedDistance_StringNullInt {

    private static final LevenshteinDetailedDistance UNLIMITED_DISTANCE =
            LevenshteinDetailedDistance.getDefaultInstance();

    @Test
    void throwsIllegalArgumentExceptionWhenRightInputIsNull() {
        assertThrows(IllegalArgumentException.class, () -> UNLIMITED_DISTANCE.apply("a", null));
    }
}
