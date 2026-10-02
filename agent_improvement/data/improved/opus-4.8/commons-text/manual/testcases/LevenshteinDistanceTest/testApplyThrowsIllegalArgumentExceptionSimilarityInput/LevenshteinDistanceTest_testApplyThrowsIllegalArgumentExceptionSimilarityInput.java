package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link LevenshteinDistance#apply(SimilarityInput, SimilarityInput)}
 * rejects {@code null} inputs by throwing an {@link IllegalArgumentException}.
 *
 * <p>A {@code null} input is invalid regardless of which side it appears on, so
 * each of the three cases below covers a different combination of null/non-null
 * arguments.</p>
 */
public class LevenshteinDistanceTest_testApplyThrowsIllegalArgumentExceptionSimilarityInput {

    /** A non-null input used as the valid counterpart in the mixed null cases. */
    private static final SimilarityCharacterInput NON_NULL_INPUT = new SimilarityCharacterInput("asdf");

    /** The distance instance under test; the threshold value is irrelevant to null validation. */
    private static final LevenshteinDistance DISTANCE = new LevenshteinDistance(0);

    @Test
    void testApplyThrowsIllegalArgumentExceptionSimilarityInput() {
        // Both inputs null.
        assertThrows(IllegalArgumentException.class,
                () -> DISTANCE.apply((SimilarityInput<Object>) null, (SimilarityInput<Object>) null));

        // Only the right-hand input is null.
        assertThrows(IllegalArgumentException.class,
                () -> DISTANCE.apply(NON_NULL_INPUT, (SimilarityCharacterInput) null));

        // Only the left-hand input is null.
        assertThrows(IllegalArgumentException.class,
                () -> DISTANCE.apply((SimilarityCharacterInput) null, NON_NULL_INPUT));
    }
}
