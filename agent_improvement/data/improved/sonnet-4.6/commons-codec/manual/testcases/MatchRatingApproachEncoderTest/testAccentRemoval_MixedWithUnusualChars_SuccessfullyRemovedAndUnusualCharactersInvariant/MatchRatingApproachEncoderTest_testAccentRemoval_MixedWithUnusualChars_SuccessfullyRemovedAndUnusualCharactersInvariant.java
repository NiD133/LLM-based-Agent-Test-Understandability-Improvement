package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testAccentRemoval_MixedWithUnusualChars_SuccessfullyRemovedAndUnusualCharactersInvariant extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /**
     * Verifies that removeAccents() strips diacritics from accented vowels (Á→A, í→i, ó→o, ú→u)
     * while leaving punctuation and other non-accented characters completely unchanged.
     */
    @Test
    final void testAccentRemoval_MixedWithUnusualChars_SuccessfullyRemovedAndUnusualCharactersInvariant() {
        // Input contains a mix of: accented vowels (Á, í, ó, ú) interleaved with
        // punctuation characters (-, ', ., ,, &) that must pass through untouched.
        String inputWithAccentsAndPunctuation = "Á-e'í.,ó&ú";

        // Accented vowels become their plain ASCII equivalents; punctuation is invariant.
        String expectedAccentsStrippedPunctuationPreserved = "A-e'i.,o&u";

        assertEquals(expectedAccentsStrippedPunctuationPreserved,
                getStringEncoder().removeAccents(inputWithAccentsAndPunctuation));
    }
}
