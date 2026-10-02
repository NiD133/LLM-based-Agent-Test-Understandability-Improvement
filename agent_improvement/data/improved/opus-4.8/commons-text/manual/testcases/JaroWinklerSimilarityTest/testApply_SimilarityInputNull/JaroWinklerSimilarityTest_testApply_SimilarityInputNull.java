package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link JaroWinklerSimilarity#apply(SimilarityInput, SimilarityInput)} rejects a {@code null} second input.
 */
public class JaroWinklerSimilarityTest_testApply_SimilarityInputNull {

    @Test
    void applyThrowsWhenSecondInputIsNull() {
        final JaroWinklerSimilarity similarity = new JaroWinklerSimilarity();
        final SimilarityInput<Character> nonNullInput = new SimilarityCharacterInput("a");

        assertThrows(IllegalArgumentException.class, () -> similarity.apply(nonNullInput, null));
    }
}
