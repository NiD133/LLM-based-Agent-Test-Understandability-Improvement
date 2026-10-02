package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testAccentRemoval_GerSpanFrenMix_SuccessfullyRemoved extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    @Test
    final void testAccentRemoval_GerSpanFrenMix_SuccessfullyRemoved() {
        // Mixed German (ä→a, ë→e, ö→o, ü→u, ß→ß, Ä→A, Ë→E, Ö→O, Ü→U),
        // Spanish (ñ→n, Ñ→N), and French (à→a) accented characters
        String inputWithAccents   = "äëöüßÄËÖÜñÑà";
        String expectedPlainAscii = "aeoußAEOUnNa";

        assertEquals(expectedPlainAscii, getStringEncoder().removeAccents(inputWithAccents));
    }
}
