package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link LevenshteinDistance#apply(CharSequence, CharSequence)}
 * rejects {@code null} inputs by throwing an {@link IllegalArgumentException}.
 */
public class LevenshteinDistanceTest_testApplyThrowsIllegalArgumentExceptionString {

    /** A distance calculator with a (non-null) threshold of zero. */
    private static final LevenshteinDistance ZERO_THRESHOLD_DISTANCE = new LevenshteinDistance(0);

    @Test
    void applyWithBothInputsNullThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> ZERO_THRESHOLD_DISTANCE.apply((String) null, (String) null));
    }
}
