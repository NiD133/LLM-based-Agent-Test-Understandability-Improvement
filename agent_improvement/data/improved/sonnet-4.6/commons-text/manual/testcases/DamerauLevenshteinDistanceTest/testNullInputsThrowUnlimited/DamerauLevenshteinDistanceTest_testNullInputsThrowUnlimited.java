package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Tests that the unlimited (no-threshold) DamerauLevenshteinDistance rejects null inputs
 * by throwing IllegalArgumentException for both CharSequence and SimilarityInput overloads.
 */
public class DamerauLevenshteinDistanceTest_testNullInputsThrowUnlimited {

    private static DamerauLevenshteinDistance defaultInstance;

    @BeforeAll
    static void createInstance() {
        // Construct the unlimited variant (null threshold)
        defaultInstance = new DamerauLevenshteinDistance();
    }

    @Test
    void testNullInputsThrowUnlimited() {
        final String nonNull = "test";
        final SimilarityInput<Character> nonNullSimilarity = SimilarityInput.input(nonNull);

        assertAll("null arguments must throw IllegalArgumentException for all apply() overloads",
            // CharSequence overload — null on the left
            () -> assertThrows(IllegalArgumentException.class,
                    () -> defaultInstance.apply((CharSequence) null, nonNull)),
            // CharSequence overload — null on the right
            () -> assertThrows(IllegalArgumentException.class,
                    () -> defaultInstance.apply(nonNull, (CharSequence) null)),
            // SimilarityInput overload — null on the left
            () -> assertThrows(IllegalArgumentException.class,
                    () -> defaultInstance.apply(null, nonNullSimilarity)),
            // SimilarityInput overload — null on the right
            () -> assertThrows(IllegalArgumentException.class,
                    () -> defaultInstance.apply(nonNullSimilarity, null))
        );
    }
}
