package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class JaroWinklerSimilarityTest_testGetJaroWinklerSimilarity_NullString {

    @Test
    void testGetJaroWinklerSimilarity_NullString() {
        final JaroWinklerSimilarity similarity = new JaroWinklerSimilarity();

        assertThrows(IllegalArgumentException.class, () -> similarity.apply(null, "clear"));
    }
}
