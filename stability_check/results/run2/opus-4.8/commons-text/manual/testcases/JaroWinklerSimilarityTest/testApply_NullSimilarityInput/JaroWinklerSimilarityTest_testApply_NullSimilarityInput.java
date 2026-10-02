package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link JaroWinklerSimilarity#apply(SimilarityInput, SimilarityInput)}
 * rejects a {@code null} input by throwing an {@link IllegalArgumentException}.
 */
public class JaroWinklerSimilarityTest_testApply_NullSimilarityInput {

    private final JaroWinklerSimilarity similarity = new JaroWinklerSimilarity();

    @Test
    void testApply_NullSimilarityInput() {
        final SimilarityInput<Character> nullLeft = null;
        final SimilarityInput<Character> nonNullRight = new SimilarityCharacterInput("a");

        assertThrows(IllegalArgumentException.class,
                () -> similarity.apply(nullLeft, nonNullRight));
    }
}
