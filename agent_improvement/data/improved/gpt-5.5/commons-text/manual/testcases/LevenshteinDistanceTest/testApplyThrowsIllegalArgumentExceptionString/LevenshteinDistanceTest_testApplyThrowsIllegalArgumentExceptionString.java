package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class LevenshteinDistanceTest_testApplyThrowsIllegalArgumentExceptionString {

    private static final int EXACT_MATCH_THRESHOLD = 0;

    @Test
    void applyWithNullStringInputsThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> new LevenshteinDistance(EXACT_MATCH_THRESHOLD).apply((String) null, (String) null));
    }
}
