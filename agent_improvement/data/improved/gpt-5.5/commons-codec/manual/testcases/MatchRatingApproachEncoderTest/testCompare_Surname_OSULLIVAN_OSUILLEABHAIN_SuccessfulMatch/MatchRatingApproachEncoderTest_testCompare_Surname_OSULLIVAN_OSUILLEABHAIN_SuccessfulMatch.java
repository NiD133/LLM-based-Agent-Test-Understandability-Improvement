package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testCompare_Surname_OSULLIVAN_OSUILLEABHAIN_SuccessfulMatch {

    @Test
    final void testCompare_Surname_OSULLIVAN_OSUILLEABHAIN_SuccessfulMatch() {
        final MatchRatingApproachEncoder encoder = new MatchRatingApproachEncoder();
        final String plainSurname = "O'Sullivan";
        final String accentedIrishSurname = "Ó ' Súilleabháin";

        assertTrue(encoder.isEncodeEquals(plainSurname, accentedIrishSurname));
    }
}
