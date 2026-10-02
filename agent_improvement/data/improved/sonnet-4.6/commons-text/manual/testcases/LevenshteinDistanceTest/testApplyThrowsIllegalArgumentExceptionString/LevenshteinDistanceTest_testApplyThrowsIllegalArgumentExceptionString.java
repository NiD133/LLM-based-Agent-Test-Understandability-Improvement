package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

public class LevenshteinDistanceTest_testApplyThrowsIllegalArgumentExceptionString {

    @Test
    void testApplyThrowsIllegalArgumentExceptionString() {
        // LevenshteinDistance requires non-null inputs regardless of threshold;
        // passing null for both left and right must throw IllegalArgumentException.
        assertThrows(IllegalArgumentException.class,
                () -> new LevenshteinDistance(0).apply((String) null, (String) null));
    }
}
