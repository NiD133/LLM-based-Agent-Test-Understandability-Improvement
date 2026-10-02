package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testCompare_COLM_COLIN_WithAccentsAndSymbolsAndSpaces_SuccessfullyMatched {

    @Test
    final void testCompare_COLM_COLIN_WithAccentsAndSymbolsAndSpaces_SuccessfullyMatched() {
        final String nameWithAccentPunctuationAndTrailingSpaces = "Cólm.   ";
        final String nameWithHyphenAndAccent = "C-olín";
        final MatchRatingApproachEncoder encoder = new MatchRatingApproachEncoder();

        assertTrue(encoder.isEncodeEquals(nameWithAccentPunctuationAndTrailingSpaces, nameWithHyphenAndAccent));
    }
}
