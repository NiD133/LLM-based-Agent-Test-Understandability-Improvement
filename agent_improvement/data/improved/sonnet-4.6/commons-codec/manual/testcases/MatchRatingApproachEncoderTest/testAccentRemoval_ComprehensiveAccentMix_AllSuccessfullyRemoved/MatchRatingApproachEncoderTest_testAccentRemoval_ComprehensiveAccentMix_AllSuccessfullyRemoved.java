package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testAccentRemoval_ComprehensiveAccentMix_AllSuccessfullyRemoved extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /**
     * Verifies that removeAccents() strips diacritics from a broad mix of accented characters —
     * grave, acute, circumflex, umlaut, and cedilla — while preserving the original letter case
     * and all non-accented characters (the commas separating each pair).
     *
     * Mapping (input → expected):
     *   uppercase grave/acute/circumflex/umlaut: È→E, É→E, Ê→E, Ë→E, Û→U, Ù→U, Ï→I, Î→I, À→A, Â→A, Ô→O
     *   lowercase grave/acute/circumflex/umlaut: è→e, é→e, ê→e, ë→e, û→u, ù→u, ï→i, î→i, à→a, â→a, ô→o
     *   cedilla: ç→c
     */
    @Test
    final void testAccentRemoval_ComprehensiveAccentMix_AllSuccessfullyRemoved() {
        String input    = "È,É,Ê,Ë,Û,Ù,Ï,Î,À,Â,Ô,è,é,ê,ë,û,ù,ï,î,à,â,ô,ç";
        String expected = "E,E,E,E,U,U,I,I,A,A,O,e,e,e,e,u,u,i,i,a,a,o,c";

        assertEquals(expected, getStringEncoder().removeAccents(input));
    }
}
