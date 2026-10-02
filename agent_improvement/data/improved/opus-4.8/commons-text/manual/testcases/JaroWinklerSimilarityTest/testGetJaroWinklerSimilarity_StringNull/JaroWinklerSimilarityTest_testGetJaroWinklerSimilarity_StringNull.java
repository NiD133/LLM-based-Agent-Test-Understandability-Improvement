package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link JaroWinklerSimilarity#apply(CharSequence, CharSequence)} rejects a {@code null} input.
 */
public class JaroWinklerSimilarityTest_testGetJaroWinklerSimilarity_StringNull {

    private final JaroWinklerSimilarity similarity = new JaroWinklerSimilarity();

    @Test
    void applyShouldThrowWhenSecondArgumentIsNull() {
        // The algorithm requires both inputs to be non-null; a null second argument must be rejected.
        assertThrows(IllegalArgumentException.class, () -> similarity.apply(" ", null));
    }
}
