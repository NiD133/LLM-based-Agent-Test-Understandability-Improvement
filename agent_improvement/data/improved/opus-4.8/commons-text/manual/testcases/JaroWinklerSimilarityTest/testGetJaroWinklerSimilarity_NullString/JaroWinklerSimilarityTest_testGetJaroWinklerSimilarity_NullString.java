package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link JaroWinklerSimilarity#apply(CharSequence, CharSequence)} rejects {@code null} input.
 */
public class JaroWinklerSimilarityTest_testGetJaroWinklerSimilarity_NullString {

    private final JaroWinklerSimilarity similarity = new JaroWinklerSimilarity();

    @Test
    void applyThrowsWhenLeftInputIsNull() {
        // A null left-hand input is invalid and must be rejected rather than scored.
        assertThrows(IllegalArgumentException.class, () -> similarity.apply(null, "clear"));
    }
}
