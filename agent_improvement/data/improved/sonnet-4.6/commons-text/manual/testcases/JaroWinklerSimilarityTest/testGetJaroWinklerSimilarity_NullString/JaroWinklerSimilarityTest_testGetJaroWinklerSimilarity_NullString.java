package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link JaroWinklerSimilarity#apply(CharSequence, CharSequence)} rejects a
 * {@code null} left argument by throwing {@link IllegalArgumentException}.
 *
 * <p>The algorithm requires both inputs to be non-null; passing {@code null} as the first
 * argument is a contract violation that must be signalled immediately.
 */
public class JaroWinklerSimilarityTest_testGetJaroWinklerSimilarity_NullString {

    private static JaroWinklerSimilarity similarity;

    @BeforeAll
    public static void setUp() {
        similarity = new JaroWinklerSimilarity();
    }

    @Test
    void testGetJaroWinklerSimilarity_NullString() {
        assertThrows(IllegalArgumentException.class, () -> similarity.apply(null, "clear"));
    }
}
