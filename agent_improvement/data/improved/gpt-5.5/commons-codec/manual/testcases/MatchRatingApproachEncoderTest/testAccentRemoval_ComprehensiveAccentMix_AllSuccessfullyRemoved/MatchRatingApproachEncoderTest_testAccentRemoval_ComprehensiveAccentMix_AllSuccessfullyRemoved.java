package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testAccentRemoval_ComprehensiveAccentMix_AllSuccessfullyRemoved {

    private final MatchRatingApproachEncoder stringEncoder = createStringEncoder();

    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    private MatchRatingApproachEncoder getStringEncoder() {
        return stringEncoder;
    }

    @Test
    final void testAccentRemoval_ComprehensiveAccentMix_AllSuccessfullyRemoved() {
        final String accentedLetters =
                "È,É,Ê,Ë,Û,Ù,Ï,Î,À,Â,Ô,è,é,ê,ë,û,ù,ï,î,à,â,ô,ç";
        final String plainLetters =
                "E,E,E,E,U,U,I,I,A,A,O,e,e,e,e,u,u,i,i,a,a,o,c";

        assertEquals(plainLetters, getStringEncoder().removeAccents(accentedLetters));
    }
}
