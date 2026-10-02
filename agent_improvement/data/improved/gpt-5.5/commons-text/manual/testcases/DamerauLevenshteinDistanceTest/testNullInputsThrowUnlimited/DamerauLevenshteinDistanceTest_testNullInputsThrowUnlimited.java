package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class DamerauLevenshteinDistanceTest_testNullInputsThrowUnlimited {

    private static final String SAMPLE_INPUT = "test";

    private static DamerauLevenshteinDistance defaultInstance;

    @BeforeAll
    static void createInstance() {
        defaultInstance = new DamerauLevenshteinDistance();
    }

    @Test
    void testNullInputsThrowUnlimited() {
        assertThrows(IllegalArgumentException.class, () -> defaultInstance.apply(null, SAMPLE_INPUT));
        assertThrows(IllegalArgumentException.class, () -> defaultInstance.apply(SAMPLE_INPUT, null));
        assertThrows(IllegalArgumentException.class, () -> defaultInstance.apply(null, SimilarityInput.input(SAMPLE_INPUT)));
        assertThrows(IllegalArgumentException.class, () -> defaultInstance.apply(SimilarityInput.input(SAMPLE_INPUT), null));
    }
}
