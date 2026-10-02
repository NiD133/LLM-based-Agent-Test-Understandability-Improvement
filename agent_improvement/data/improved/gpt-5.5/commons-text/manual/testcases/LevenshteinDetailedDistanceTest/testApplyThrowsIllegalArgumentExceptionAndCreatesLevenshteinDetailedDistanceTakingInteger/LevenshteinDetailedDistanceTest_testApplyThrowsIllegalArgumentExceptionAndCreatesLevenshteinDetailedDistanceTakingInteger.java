package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.text.TextStringBuilder;
import org.junit.jupiter.api.Test;

public class LevenshteinDetailedDistanceTest_testApplyThrowsIllegalArgumentExceptionAndCreatesLevenshteinDetailedDistanceTakingInteger {

    @Test
    void throwsIllegalArgumentExceptionWhenRightSequenceIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            final LevenshteinDetailedDistance distanceWithZeroThreshold = new LevenshteinDetailedDistance(0);
            final CharSequence leftSequence = new TextStringBuilder();

            distanceWithZeroThreshold.apply(leftSequence, null);
        });
    }
}
