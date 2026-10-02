package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies {@link MatchRatingApproachEncoder#removeAccents(String)} on input that mixes
 * accented letters with spaces.
 */
public class MatchRatingApproachEncoderTest_testAccentRemoval_WithSpaces_SuccessfullyRemovedAndSpacesInvariant
        extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /**
     * removeAccents should strip the accents from each accented letter while leaving the
     * existing spaces (including the run of two consecutive spaces) exactly as they were.
     */
    @Test
    final void testAccentRemoval_WithSpaces_SuccessfullyRemovedAndSpacesInvariant() {
        // "áé íó  ú" -> accents removed to "ae io  u"; the single and double spaces are preserved.
        final String accentedInput = "áé íó  ú";
        final String expectedDeaccented = "ae io  u";

        assertEquals(expectedDeaccented, getStringEncoder().removeAccents(accentedInput));
    }
}
