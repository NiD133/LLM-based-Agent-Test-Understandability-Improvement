package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link JaroWinklerSimilarity#apply(SimilarityInput, SimilarityInput)}
 * rejects a {@code null} input.
 */
public class JaroWinklerSimilarityTest_testApply_NullSimilarityInput {

    /** Shared instance under test; stateless, so it can be reused across tests. */
    private static JaroWinklerSimilarity similarity;

    @BeforeAll
    public static void setUp() {
        similarity = new JaroWinklerSimilarity();
    }

    @Test
    void testApply_NullSimilarityInput() {
        // A null left input must be rejected, even when the right input is valid.
        final SimilarityInput<Character> validRightInput = new SimilarityCharacterInput("a");

        assertThrows(IllegalArgumentException.class, () -> similarity.apply(null, validRightInput));
    }
}
