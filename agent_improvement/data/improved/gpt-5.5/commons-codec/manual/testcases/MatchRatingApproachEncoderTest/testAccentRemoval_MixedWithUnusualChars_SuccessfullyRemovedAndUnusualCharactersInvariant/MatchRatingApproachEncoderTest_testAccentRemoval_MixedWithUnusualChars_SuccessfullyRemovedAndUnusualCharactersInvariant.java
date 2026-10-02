package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testAccentRemoval_MixedWithUnusualChars_SuccessfullyRemovedAndUnusualCharactersInvariant {

    @Test
    final void testAccentRemoval_MixedWithUnusualChars_SuccessfullyRemovedAndUnusualCharactersInvariant() {
        final String accentedWord = "Á-e'í.,ó&ú";
        final String expectedWithoutAccents = "A-e'i.,o&u";

        assertEquals(expectedWithoutAccents, new MatchRatingApproachEncoder().removeAccents(accentedWord));
    }
}
