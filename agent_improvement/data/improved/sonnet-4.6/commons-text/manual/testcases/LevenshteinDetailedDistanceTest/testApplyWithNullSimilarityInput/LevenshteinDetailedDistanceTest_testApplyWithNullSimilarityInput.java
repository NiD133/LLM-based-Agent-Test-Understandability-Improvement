package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

public class LevenshteinDetailedDistanceTest_testApplyWithNullSimilarityInput {

    @Test
    void testApplyWithNullSimilarityInput() {
        final LevenshteinDetailedDistance limitedDistance = new LevenshteinDetailedDistance(0);
        final LevenshteinDetailedDistance unlimitedDistance = new LevenshteinDetailedDistance(null);
        final SimilarityCharacterInput nonNullInput = new SimilarityCharacterInput("asdf");

        // Both inputs null with a limited-threshold instance
        assertThrows(IllegalArgumentException.class,
                () -> limitedDistance.apply((SimilarityInput<Object>) null, (SimilarityInput<Object>) null));

        // Right input null with a limited-threshold instance
        assertThrows(IllegalArgumentException.class,
                () -> limitedDistance.apply(nonNullInput, (SimilarityCharacterInput) null));

        // Left input null with a limited-threshold instance
        assertThrows(IllegalArgumentException.class,
                () -> limitedDistance.apply((SimilarityCharacterInput) null, nonNullInput));

        // Right input null with an unlimited (null-threshold) instance
        assertThrows(IllegalArgumentException.class,
                () -> unlimitedDistance.apply(nonNullInput, (SimilarityCharacterInput) null));

        // Left input null with an unlimited (null-threshold) instance
        assertThrows(IllegalArgumentException.class,
                () -> unlimitedDistance.apply((SimilarityCharacterInput) null, nonNullInput));
    }
}
