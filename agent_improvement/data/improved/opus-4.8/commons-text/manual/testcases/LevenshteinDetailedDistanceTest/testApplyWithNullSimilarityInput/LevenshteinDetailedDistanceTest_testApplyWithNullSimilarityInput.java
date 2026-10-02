package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link LevenshteinDetailedDistance#apply(SimilarityInput, SimilarityInput)}
 * rejects {@code null} inputs by throwing an {@link IllegalArgumentException}.
 *
 * <p>The rejection must happen regardless of which argument is {@code null} (left, right, or both),
 * and regardless of whether the distance is configured with a numeric threshold or with the
 * unlimited (null threshold) variant.</p>
 */
public class LevenshteinDetailedDistanceTest_testApplyWithNullSimilarityInput {

    /** A non-null input used as the valid counterpart to a null argument. */
    private static final SimilarityCharacterInput NON_NULL_INPUT = new SimilarityCharacterInput("asdf");

    /** Distance configured with a numeric threshold (uses the limited-compare path). */
    private static final LevenshteinDetailedDistance THRESHOLDED_DISTANCE = new LevenshteinDetailedDistance(0);

    /** Distance configured with a null threshold (uses the unlimited-compare path). */
    private static final LevenshteinDetailedDistance UNLIMITED_DISTANCE = new LevenshteinDetailedDistance(null);

    @Test
    void testApplyWithNullSimilarityInput() {
        // Thresholded distance: both inputs null.
        assertThrows(IllegalArgumentException.class,
                () -> THRESHOLDED_DISTANCE.apply((SimilarityInput<Object>) null, (SimilarityInput<Object>) null));

        // Thresholded distance: only one of the two inputs is null.
        assertThrows(IllegalArgumentException.class,
                () -> THRESHOLDED_DISTANCE.apply(NON_NULL_INPUT, (SimilarityCharacterInput) null));
        assertThrows(IllegalArgumentException.class,
                () -> THRESHOLDED_DISTANCE.apply((SimilarityCharacterInput) null, NON_NULL_INPUT));

        // Unlimited distance: only one of the two inputs is null.
        assertThrows(IllegalArgumentException.class,
                () -> UNLIMITED_DISTANCE.apply(NON_NULL_INPUT, (SimilarityCharacterInput) null));
        assertThrows(IllegalArgumentException.class,
                () -> UNLIMITED_DISTANCE.apply((SimilarityCharacterInput) null, NON_NULL_INPUT));
    }
}
