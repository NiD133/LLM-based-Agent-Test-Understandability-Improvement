package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class JaroWinklerSimilarityTest_testApply_SimilarityInputNull {

    private static JaroWinklerSimilarity similarity;

    @BeforeAll
    public static void setUp() {
        similarity = new JaroWinklerSimilarity();
    }

    /** Verifies that apply throws IllegalArgumentException when the right input is null. */
    @Test
    void testApply_SimilarityInputNull() {
        assertThrows(IllegalArgumentException.class, () -> similarity.apply(new SimilarityCharacterInput("a"), null));
    }
}
