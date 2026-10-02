package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Verifies that the default (unlimited, no-threshold) {@link DamerauLevenshteinDistance}
 * rejects {@code null} inputs by throwing {@link IllegalArgumentException}.
 */
public class DamerauLevenshteinDistanceTest_testNullInputsThrowUnlimited {

    /** The default instance uses the unlimited algorithm because no threshold is supplied. */
    private static DamerauLevenshteinDistance unlimitedDistance;

    @BeforeAll
    static void createUnlimitedInstance() {
        unlimitedDistance = new DamerauLevenshteinDistance();
    }

    @Test
    void testNullInputsThrowUnlimited() {
        // A null argument is invalid regardless of which side it is on or which
        // apply(...) overload (CharSequence or SimilarityInput) receives it.
        assertThrows(IllegalArgumentException.class,
                () -> unlimitedDistance.apply(null, "test"));
        assertThrows(IllegalArgumentException.class,
                () -> unlimitedDistance.apply("test", null));
        assertThrows(IllegalArgumentException.class,
                () -> unlimitedDistance.apply(null, SimilarityInput.input("test")));
        assertThrows(IllegalArgumentException.class,
                () -> unlimitedDistance.apply(SimilarityInput.input("test"), null));
    }
}
