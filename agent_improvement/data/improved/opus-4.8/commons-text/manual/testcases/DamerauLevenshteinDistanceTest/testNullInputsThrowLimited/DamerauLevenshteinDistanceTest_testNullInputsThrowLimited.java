package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that a threshold-limited {@link DamerauLevenshteinDistance} rejects {@code null} inputs
 * by throwing {@link IllegalArgumentException}, regardless of which {@code apply} overload is used
 * or on which side ({@code left} / {@code right}) the {@code null} appears.
 */
public class DamerauLevenshteinDistanceTest_testNullInputsThrowLimited {

    /** Any non-negative threshold selects the limited algorithm; the exact value is irrelevant here. */
    private static final int THRESHOLD = 10;

    @Test
    void testNullInputsThrowLimited() {
        final DamerauLevenshteinDistance limitedDistance = new DamerauLevenshteinDistance(THRESHOLD);

        // CharSequence overload: null on either side must be rejected.
        assertThrows(IllegalArgumentException.class, () -> limitedDistance.apply(null, "test"));
        assertThrows(IllegalArgumentException.class, () -> limitedDistance.apply("test", null));

        // SimilarityInput overload: null on either side must be rejected.
        assertThrows(IllegalArgumentException.class, () -> limitedDistance.apply(null, SimilarityInput.input("test")));
        assertThrows(IllegalArgumentException.class, () -> limitedDistance.apply(SimilarityInput.input("test"), null));
    }
}
