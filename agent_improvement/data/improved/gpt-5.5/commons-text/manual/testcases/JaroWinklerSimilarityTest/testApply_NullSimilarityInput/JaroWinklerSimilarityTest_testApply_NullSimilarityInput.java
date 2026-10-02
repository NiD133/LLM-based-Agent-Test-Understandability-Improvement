package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class JaroWinklerSimilarityTest_testApply_NullSimilarityInput {

    private static JaroWinklerSimilarity similarity;

    @BeforeAll
    public static void setUp() {
        similarity = new JaroWinklerSimilarity();
    }

    @Test
    void testApply_NullSimilarityInput() {
        assertThrows(IllegalArgumentException.class, () -> similarity.apply(null, new SimilarityCharacterInput("a")));
    }
}
