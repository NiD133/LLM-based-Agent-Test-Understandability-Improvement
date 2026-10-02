package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testCompare_COLM_COLIN_WithAccentsAndSymbolsAndSpaces_SuccessfullyMatched extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /**
     * Verifies that "COLM" and "COLIN" are considered phonetically equivalent even when
     * the inputs contain accented characters (ó, í), punctuation symbols (dot, hyphen),
     * and extra whitespace. The encoder is expected to normalise all of these before
     * comparing, so the match should still succeed.
     */
    @Test
    final void testCompare_COLM_COLIN_WithAccentsAndSymbolsAndSpaces_SuccessfullyMatched() {
        // "Cólm.   " — accented vowel, trailing dot, trailing spaces  → normalises to COLM
        String colmWithAccentDotAndSpaces = "Cólm.   ";
        // "C-olín"  — leading hyphen, accented vowel                  → normalises to COLIN
        String colinWithHyphenAndAccent   = "C-olín";

        assertTrue(getStringEncoder().isEncodeEquals(colmWithAccentDotAndSpaces, colinWithHyphenAndAccent));
    }
}
