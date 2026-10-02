package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link MatchRatingApproachEncoder#isEncodeEquals(String, String)} treats two
 * phonetic spellings of the same surname as a match.
 */
public class MatchRatingApproachEncoderTest_testCompare_Surname_PRZEMYSL_PSHEMESHIL_SuccessfullyMatched extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /**
     * The Polish surname "Przemysl" and its English phonetic transcription "Pshemeshil"
     * should be recognized as the same name. Letters are spaced out here only to make the
     * pronunciation explicit; the encoder ignores the spaces during cleaning.
     */
    @Test
    final void testCompare_Surname_PRZEMYSL_PSHEMESHIL_SuccessfullyMatched() {
        final String spelledPrzemysl = " P rz e m y s l";
        final String phoneticPshemeshil = " P sh e m e sh i l";

        final boolean namesMatch =
                getStringEncoder().isEncodeEquals(spelledPrzemysl, phoneticPshemeshil);

        assertTrue(namesMatch, "Przemysl and its phonetic spelling Pshemeshil should match");
    }
}
