package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.apache.commons.text.TextStringBuilder;
import org.junit.jupiter.api.Test;

public class LevenshteinDetailedDistanceTest_testApplyThrowsIllegalArgumentExceptionAndCreatesLevenshteinDetailedDistanceTakingInteger {

    @Test
    void applyThrowsWhenSecondArgumentIsNull() {
        // A threshold of 0 activates the limited-compare path, which rejects null inputs.
        final LevenshteinDetailedDistance distance = new LevenshteinDetailedDistance(0);
        final CharSequence leftInput = new TextStringBuilder();

        assertThrows(IllegalArgumentException.class, () -> distance.apply(leftInput, null));
    }
}
