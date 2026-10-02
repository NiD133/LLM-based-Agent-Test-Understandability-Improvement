package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testAccentRemoval_GerSpanFrenMix_SuccessfullyRemoved {

    @Test
    final void testAccentRemoval_GerSpanFrenMix_SuccessfullyRemoved() {
        final String accentedGermanSpanishFrenchCharacters = "äëöüßÄËÖÜñÑà";
        final String expectedWithoutRemovableAccents = "aeoußAEOUnNa";

        assertEquals(expectedWithoutRemovableAccents,
                new MatchRatingApproachEncoder().removeAccents(accentedGermanSpanishFrenchCharacters));
    }
}
