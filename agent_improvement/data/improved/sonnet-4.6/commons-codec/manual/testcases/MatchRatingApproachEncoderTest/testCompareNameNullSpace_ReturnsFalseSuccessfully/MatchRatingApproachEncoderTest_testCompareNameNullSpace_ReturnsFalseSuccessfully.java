package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertFalse;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testCompareNameNullSpace_ReturnsFalseSuccessfully extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /**
     * Verifies that isEncodeEquals returns false when the first name is null,
     * because a null input is treated as a trivial/invalid name and comparison
     * is short-circuited immediately without consulting the second argument.
     */
    @Test
    final void testCompareNameNullSpace_ReturnsFalseSuccessfully() {
        // null as the first name is an invalid input; the encoder must reject it
        // regardless of the second argument (" " — a single space)
        assertFalse(getStringEncoder().isEncodeEquals(null, " "));
    }
}
