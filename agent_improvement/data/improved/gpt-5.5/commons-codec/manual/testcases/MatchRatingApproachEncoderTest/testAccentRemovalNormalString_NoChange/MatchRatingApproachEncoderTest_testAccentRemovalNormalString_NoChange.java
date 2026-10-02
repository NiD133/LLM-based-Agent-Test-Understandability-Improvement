package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testAccentRemovalNormalString_NoChange {

    private static final String ASCII_SENTENCE = "Colorless green ideas sleep furiously";

    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    private MatchRatingApproachEncoder getStringEncoder() {
        return createStringEncoder();
    }

    @Test
    final void testAccentRemovalNormalString_NoChange() {
        assertEquals(ASCII_SENTENCE, getStringEncoder().removeAccents(ASCII_SENTENCE));
    }
}
