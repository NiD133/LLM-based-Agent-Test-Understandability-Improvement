package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class JaroWinklerSimilarityTest_testGetJaroWinklerSimilarity_NullNull {

    private static JaroWinklerSimilarity similarity;

    @BeforeAll
    public static void setUp() {
        similarity = new JaroWinklerSimilarity();
    }

    @Test
    @DisplayName("apply(null, null) should throw IllegalArgumentException")
    void testGetJaroWinklerSimilarity_NullNull() {
        assertThrows(IllegalArgumentException.class, () -> similarity.apply((String) null, null));
    }
}
