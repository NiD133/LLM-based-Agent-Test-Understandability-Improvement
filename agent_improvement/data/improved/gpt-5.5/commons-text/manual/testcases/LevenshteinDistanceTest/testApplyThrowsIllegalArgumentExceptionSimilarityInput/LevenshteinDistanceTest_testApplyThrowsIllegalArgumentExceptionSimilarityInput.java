package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class LevenshteinDistanceTest_testApplyThrowsIllegalArgumentExceptionSimilarityInput {

    @Test
    void testApplyThrowsIllegalArgumentExceptionSimilarityInput() {
        assertBothInputsNullAreRejected();
        assertRightInputNullIsRejected();
        assertLeftInputNullIsRejected();
    }

    private static void assertBothInputsNullAreRejected() {
        assertThrows(IllegalArgumentException.class, () -> new LevenshteinDistance(0)
                .apply((SimilarityInput<Object>) null, (SimilarityInput<Object>) null));
    }

    private static void assertRightInputNullIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> new LevenshteinDistance(0)
                .apply(new SimilarityCharacterInput("asdf"), (SimilarityCharacterInput) null));
    }

    private static void assertLeftInputNullIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> new LevenshteinDistance(0)
                .apply((SimilarityCharacterInput) null, new SimilarityCharacterInput("asdf")));
    }
}
