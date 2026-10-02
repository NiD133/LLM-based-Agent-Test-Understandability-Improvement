package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class DamerauLevenshteinDistanceTest_testNullInputsThrowLimited {

    private static final int THRESHOLD = 10;
    private static final String NON_NULL_TEXT = "test";

    @Test
    void testNullInputsThrowLimited() {
        final DamerauLevenshteinDistance distance = new DamerauLevenshteinDistance(THRESHOLD);

        assertThrows(IllegalArgumentException.class, () -> distance.apply(null, NON_NULL_TEXT));
        assertThrows(IllegalArgumentException.class, () -> distance.apply(NON_NULL_TEXT, null));
        assertThrows(IllegalArgumentException.class, () -> distance.apply(null, SimilarityInput.input(NON_NULL_TEXT)));
        assertThrows(IllegalArgumentException.class, () -> distance.apply(SimilarityInput.input(NON_NULL_TEXT), null));
    }
}
