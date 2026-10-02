package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.apache.commons.text.TextStringBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class LevenshteinDetailedDistanceTest_testApplyWithNullSimilarityInput {

    private static final LevenshteinDetailedDistance UNLIMITED_DISTANCE = LevenshteinDetailedDistance.getDefaultInstance();

    @Test
    void testApplyWithNullSimilarityInput() {
        assertThrows(IllegalArgumentException.class, () -> new LevenshteinDetailedDistance(0).apply((SimilarityInput<Object>) null, (SimilarityInput<Object>) null));
        assertThrows(IllegalArgumentException.class, () -> new LevenshteinDetailedDistance(0).apply(new SimilarityCharacterInput("asdf"), (SimilarityCharacterInput) null));
        assertThrows(IllegalArgumentException.class, () -> new LevenshteinDetailedDistance(0).apply((SimilarityCharacterInput) null, new SimilarityCharacterInput("asdf")));
        assertThrows(IllegalArgumentException.class, () -> new LevenshteinDetailedDistance(null).apply(new SimilarityCharacterInput("asdf"), (SimilarityCharacterInput) null));
        assertThrows(IllegalArgumentException.class, () -> new LevenshteinDetailedDistance(null).apply((SimilarityCharacterInput) null, new SimilarityCharacterInput("asdf")));
    }
}
