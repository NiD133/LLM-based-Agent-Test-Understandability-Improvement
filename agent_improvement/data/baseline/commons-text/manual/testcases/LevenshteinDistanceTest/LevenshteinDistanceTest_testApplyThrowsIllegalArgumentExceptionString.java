package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class LevenshteinDistanceTest_testApplyThrowsIllegalArgumentExceptionString {

    private static final LevenshteinDistance UNLIMITED_DISTANCE = LevenshteinDistance.getDefaultInstance();

    @Test
    void testApplyThrowsIllegalArgumentExceptionString() {
        assertThrows(IllegalArgumentException.class, () -> new LevenshteinDistance(0).apply((String) null, (String) null));
    }
}
