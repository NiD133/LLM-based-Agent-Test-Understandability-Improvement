package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class LevenshteinDistanceTest_testApplyThrowsIllegalArgumentExceptionSimilarityInput {

    private static final LevenshteinDistance UNLIMITED_DISTANCE = LevenshteinDistance.getDefaultInstance();

    @Test
    void testApplyThrowsIllegalArgumentExceptionSimilarityInput() {
        assertThrows(IllegalArgumentException.class, () -> new LevenshteinDistance(0).apply((SimilarityInput<Object>) null, (SimilarityInput<Object>) null));
        assertThrows(IllegalArgumentException.class, () -> new LevenshteinDistance(0).apply(new SimilarityCharacterInput("asdf"), (SimilarityCharacterInput) null));
        assertThrows(IllegalArgumentException.class, () -> new LevenshteinDistance(0).apply((SimilarityCharacterInput) null, new SimilarityCharacterInput("asdf")));
    }
}
