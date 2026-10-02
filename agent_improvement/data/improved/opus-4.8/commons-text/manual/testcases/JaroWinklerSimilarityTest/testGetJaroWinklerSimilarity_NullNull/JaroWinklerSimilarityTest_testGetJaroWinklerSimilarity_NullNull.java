package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link JaroWinklerSimilarity#apply(CharSequence, CharSequence)} rejects
 * {@code null} inputs.
 */
public class JaroWinklerSimilarityTest_testGetJaroWinklerSimilarity_NullNull {

    private final JaroWinklerSimilarity similarity = new JaroWinklerSimilarity();

    @Test
    void testGetJaroWinklerSimilarity_NullNull() {
        // Both inputs are null, so apply(...) must reject them with an IllegalArgumentException.
        assertThrows(IllegalArgumentException.class, () -> similarity.apply((String) null, null));
    }
}
