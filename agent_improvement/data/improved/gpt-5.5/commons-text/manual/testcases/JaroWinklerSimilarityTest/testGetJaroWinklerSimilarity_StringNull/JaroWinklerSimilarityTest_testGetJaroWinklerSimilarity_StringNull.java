package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class JaroWinklerSimilarityTest_testGetJaroWinklerSimilarity_StringNull {

    private static final String NON_NULL_LEFT_INPUT = " ";

    private static JaroWinklerSimilarity similarity;

    @BeforeAll
    public static void setUp() {
        similarity = new JaroWinklerSimilarity();
    }

    @Test
    void testGetJaroWinklerSimilarity_StringNull() {
        assertThrows(IllegalArgumentException.class, () -> similarity.apply(NON_NULL_LEFT_INPUT, null));
    }
}
