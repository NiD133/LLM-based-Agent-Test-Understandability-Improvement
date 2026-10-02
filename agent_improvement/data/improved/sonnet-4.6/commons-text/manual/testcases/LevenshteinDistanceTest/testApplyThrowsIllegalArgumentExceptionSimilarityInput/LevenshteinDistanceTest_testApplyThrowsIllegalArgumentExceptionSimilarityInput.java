package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("LevenshteinDistance.apply(SimilarityInput, SimilarityInput) — null argument validation")
public class LevenshteinDistanceTest_testApplyThrowsIllegalArgumentExceptionSimilarityInput {

    // A threshold of 0 is sufficient to trigger the null-check; the value itself does not affect which exception is thrown.
    private static final LevenshteinDistance DISTANCE_WITH_THRESHOLD = new LevenshteinDistance(0);

    @Test
    @DisplayName("throws IllegalArgumentException when both left and right inputs are null")
    void applyThrowsWhenBothInputsAreNull() {
        assertThrows(IllegalArgumentException.class,
                () -> DISTANCE_WITH_THRESHOLD.apply(
                        (SimilarityInput<Object>) null,
                        (SimilarityInput<Object>) null));
    }

    @Test
    @DisplayName("throws IllegalArgumentException when right input is null")
    void applyThrowsWhenRightInputIsNull() {
        SimilarityCharacterInput leftInput = new SimilarityCharacterInput("asdf");
        assertThrows(IllegalArgumentException.class,
                () -> DISTANCE_WITH_THRESHOLD.apply(leftInput, (SimilarityCharacterInput) null));
    }

    @Test
    @DisplayName("throws IllegalArgumentException when left input is null")
    void applyThrowsWhenLeftInputIsNull() {
        SimilarityCharacterInput rightInput = new SimilarityCharacterInput("asdf");
        assertThrows(IllegalArgumentException.class,
                () -> DISTANCE_WITH_THRESHOLD.apply((SimilarityCharacterInput) null, rightInput));
    }
}
