package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testGetEncoding_SMYTH_to_SMYTH extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /**
     * Verifies that "Smyth" encodes to "SMYTH" under the Match Rating Approach algorithm.
     * The MRA algorithm uppercases the name, removes non-leading vowels (none present here
     * since Y is not treated as a vowel), removes double consonants (none present), and
     * then returns the first+last 3 characters — but since the result is only 5 characters,
     * it is returned unchanged as "SMYTH".
     */
    @Test
    final void testGetEncoding_SMYTH_to_SMYTH() {
        final String mixedCaseInput = "Smyth";
        final String expectedMraCode = "SMYTH";

        assertEquals(expectedMraCode, getStringEncoder().encode(mixedCaseInput));
    }
}
