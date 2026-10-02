package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testCompare_MCGOWAN_MCGEOGHEGAN_SuccessfullyMatched extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /**
     * "McGowan" and "Mc Geoghegan" are variant spellings of the same Irish surname.
     * The MRA algorithm should recognise them as phonetically equivalent after
     * cleaning (space removal, vowel removal, double-consonant collapsing) and
     * comparing the resulting codes.
     */
    @Test
    final void testCompare_MCGOWAN_MCGEOGHEGAN_SuccessfullyMatched() {
        assertTrue(getStringEncoder().isEncodeEquals("McGowan", "Mc Geoghegan"));
    }
}
