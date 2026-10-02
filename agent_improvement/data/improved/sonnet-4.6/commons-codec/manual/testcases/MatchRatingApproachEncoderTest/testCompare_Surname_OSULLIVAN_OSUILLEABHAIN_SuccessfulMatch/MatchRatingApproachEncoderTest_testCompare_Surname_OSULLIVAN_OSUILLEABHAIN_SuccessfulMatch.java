package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testCompare_Surname_OSULLIVAN_OSUILLEABHAIN_SuccessfulMatch extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /**
     * Verifies that the anglicised form "O'Sullivan" and the Irish-language form "Ó ' Súilleabháin"
     * are recognised as phonetically equivalent by the MRA algorithm. Accents, punctuation, and
     * spacing differences are normalised before comparison, so both names should encode to the same
     * MRA key and yield a match.
     */
    @Test
    final void testCompare_Surname_OSULLIVAN_OSUILLEABHAIN_SuccessfulMatch() {
        assertTrue(getStringEncoder().isEncodeEquals("O'Sullivan", "Ó ' Súilleabháin"));
    }
}
