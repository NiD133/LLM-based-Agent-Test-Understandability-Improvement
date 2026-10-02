package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies {@link MatchRatingApproachEncoder#removeAccents(String)} on input that mixes
 * accented letters with punctuation and other non-letter symbols.
 */
public class MatchRatingApproachEncoderTest_testAccentRemoval_MixedWithUnusualChars_SuccessfullyRemovedAndUnusualCharactersInvariant extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /**
     * Accented letters should be replaced by their plain ASCII equivalents (with case preserved),
     * while punctuation and other unusual characters are left untouched.
     */
    @Test
    final void testAccentRemoval_MixedWithUnusualChars_SuccessfullyRemovedAndUnusualCharactersInvariant() {
        // Accented letters: Á -> A, í -> i, ó -> o, ú -> u.
        // Unusual characters that must remain unchanged: '-', '\'', '.', ',', '&'.
        final String inputWithAccentsAndSymbols = "Á-e'í.,ó&ú";
        final String expectedDeaccentedText = "A-e'i.,o&u";

        final String actualDeaccentedText =
                getStringEncoder().removeAccents(inputWithAccentsAndSymbols);

        assertEquals(expectedDeaccentedText, actualDeaccentedText);
    }
}
