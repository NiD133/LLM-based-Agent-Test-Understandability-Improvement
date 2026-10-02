package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link JaroWinklerSimilarity#apply(SimilarityInput, SimilarityInput)}
 * rejects a {@code null} input.
 */
public class JaroWinklerSimilarityTest_testApply_NullSimilarityInput {

    private final JaroWinklerSimilarity similarity = new JaroWinklerSimilarity();

    @Test
    void testApply_NullSimilarityInput() {
        // The contract states both inputs must be non-null; passing a null
        // first argument must raise an IllegalArgumentException.
        final SimilarityInput<Character> nonNullInput = new SimilarityCharacterInput("a");

        assertThrows(IllegalArgumentException.class,
                () -> similarity.apply(null, nonNullInput));
    }
}
