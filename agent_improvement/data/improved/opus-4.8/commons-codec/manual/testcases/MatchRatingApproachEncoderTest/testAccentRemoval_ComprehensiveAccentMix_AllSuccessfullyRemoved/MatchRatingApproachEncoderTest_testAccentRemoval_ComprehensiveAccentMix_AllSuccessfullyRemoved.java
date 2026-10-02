package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testAccentRemoval_ComprehensiveAccentMix_AllSuccessfullyRemoved extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /**
     * Verifies that {@link MatchRatingApproachEncoder#removeAccents(String)} strips the
     * diacritical mark from every accented letter while preserving the original case and
     * leaving the (unaccented) comma separators untouched.
     *
     * <p>Each accented character maps to its plain ASCII equivalent, for example:
     * {@code È -> E}, {@code Ù -> U}, {@code à -> a}, {@code ç -> c}.</p>
     */
    @Test
    final void testAccentRemoval_ComprehensiveAccentMix_AllSuccessfullyRemoved() {
        // Comma-separated mix of upper- and lower-case accented letters (grave, acute,
        // circumflex, umlaut and cedilla) spanning the most common Latin diacritics.
        final String accentedLetters = "È,É,Ê,Ë,Û,Ù,Ï,Î,À,Â,Ô,è,é,ê,ë,û,ù,ï,î,à,â,ô,ç";

        // The same letters with their accents removed; case and commas are preserved.
        final String expectedPlainLetters = "E,E,E,E,U,U,I,I,A,A,O,e,e,e,e,u,u,i,i,a,a,o,c";

        assertEquals(expectedPlainLetters, getStringEncoder().removeAccents(accentedLetters));
    }
}
