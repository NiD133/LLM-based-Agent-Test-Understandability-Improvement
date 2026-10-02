package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class JaroWinklerSimilarityTest_testApply_NullSimilarityInput {

    @Test
    void testApply_NullSimilarityInput() {
        final JaroWinklerSimilarity similarity = new JaroWinklerSimilarity();
        final SimilarityCharacterInput nonNullRightInput = new SimilarityCharacterInput("a");

        assertThrows(
                IllegalArgumentException.class,
                () -> similarity.apply(null, nonNullRightInput));
    }
}
