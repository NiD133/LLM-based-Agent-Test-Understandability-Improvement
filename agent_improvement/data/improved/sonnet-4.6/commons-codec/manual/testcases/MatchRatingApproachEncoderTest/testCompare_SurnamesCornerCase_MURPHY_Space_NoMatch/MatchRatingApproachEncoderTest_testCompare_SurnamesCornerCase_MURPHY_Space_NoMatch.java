package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertFalse;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testCompare_SurnamesCornerCase_MURPHY_Space_NoMatch extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /**
     * Verifies that a blank/space-only string is treated as an invalid input by isEncodeEquals,
     * resulting in no match even against a valid surname like "Murphy".
     * The MRA algorithm explicitly rejects inputs that are null, empty, or consist solely of a space.
     */
    @Test
    final void testCompare_SurnamesCornerCase_MURPHY_Space_NoMatch() {
        final String validSurname = "Murphy";
        final String blankInput = " ";

        assertFalse(
            getStringEncoder().isEncodeEquals(validSurname, blankInput),
            "A space-only string should never match any surname under MRA — it is an invalid input"
        );
    }
}
