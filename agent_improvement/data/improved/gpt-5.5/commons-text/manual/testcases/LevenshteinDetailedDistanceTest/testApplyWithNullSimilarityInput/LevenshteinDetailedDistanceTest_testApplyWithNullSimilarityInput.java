package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class LevenshteinDetailedDistanceTest_testApplyWithNullSimilarityInput {

    @Test
    void testApplyWithNullSimilarityInput() {
        assertThrows(IllegalArgumentException.class,
                () -> new LevenshteinDetailedDistance(0).apply(
                        (SimilarityInput<Object>) null,
                        (SimilarityInput<Object>) null));

        assertThrows(IllegalArgumentException.class,
                () -> new LevenshteinDetailedDistance(0).apply(
                        new SimilarityCharacterInput("asdf"),
                        (SimilarityCharacterInput) null));

        assertThrows(IllegalArgumentException.class,
                () -> new LevenshteinDetailedDistance(0).apply(
                        (SimilarityCharacterInput) null,
                        new SimilarityCharacterInput("asdf")));

        assertThrows(IllegalArgumentException.class,
                () -> new LevenshteinDetailedDistance(null).apply(
                        new SimilarityCharacterInput("asdf"),
                        (SimilarityCharacterInput) null));

        assertThrows(IllegalArgumentException.class,
                () -> new LevenshteinDetailedDistance(null).apply(
                        (SimilarityCharacterInput) null,
                        new SimilarityCharacterInput("asdf")));
    }
}
