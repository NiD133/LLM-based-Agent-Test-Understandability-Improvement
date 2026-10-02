package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class JaroWinklerSimilarityTest_testGetJaroWinklerSimilarity_StringNull {

    private static JaroWinklerSimilarity similarity;

    @BeforeAll
    public static void setUp() {
        similarity = new JaroWinklerSimilarity();
    }

    @Test
    void testGetJaroWinklerSimilarity_StringNull() {
        // Passing null as the second argument must throw IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> similarity.apply(" ", null));
    }
}
