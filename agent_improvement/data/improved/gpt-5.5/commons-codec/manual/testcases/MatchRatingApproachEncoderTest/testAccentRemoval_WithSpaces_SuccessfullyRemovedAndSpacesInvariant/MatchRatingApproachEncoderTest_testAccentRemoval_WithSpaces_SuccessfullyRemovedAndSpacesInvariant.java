package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testAccentRemoval_WithSpaces_SuccessfullyRemovedAndSpacesInvariant {

    @Test
    final void testAccentRemoval_WithSpaces_SuccessfullyRemovedAndSpacesInvariant() {
        final MatchRatingApproachEncoder encoder = new MatchRatingApproachEncoder();
        final String accentedTextWithSpaces = "áé íó  ú";
        final String expectedTextWithAccentsRemovedAndSpacingPreserved = "ae io  u";

        assertEquals(expectedTextWithAccentsRemovedAndSpacingPreserved,
                encoder.removeAccents(accentedTextWithSpaces));
    }
}
