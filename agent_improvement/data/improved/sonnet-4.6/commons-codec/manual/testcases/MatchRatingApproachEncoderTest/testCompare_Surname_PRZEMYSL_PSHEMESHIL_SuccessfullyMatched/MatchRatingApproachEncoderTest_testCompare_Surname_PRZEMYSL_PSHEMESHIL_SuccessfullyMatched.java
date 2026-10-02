package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testCompare_Surname_PRZEMYSL_PSHEMESHIL_SuccessfullyMatched extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /**
     * Verifies that the MRA algorithm treats "Przemysl" and "Pshemeshil" as phonetically equivalent.
     * These are two different transliterations of the Polish surname/city name "Przemyśl".
     * The spaced-out inputs exercise whitespace handling in the encoder before comparison.
     */
    @Test
    final void testCompare_Surname_PRZEMYSL_PSHEMESHIL_SuccessfullyMatched() {
        String polishSurnameSpellingA = " P rz e m y s l";
        String polishSurnameSpellingB = " P sh e m e sh i l";

        assertTrue(getStringEncoder().isEncodeEquals(polishSurnameSpellingA, polishSurnameSpellingB));
    }
}
