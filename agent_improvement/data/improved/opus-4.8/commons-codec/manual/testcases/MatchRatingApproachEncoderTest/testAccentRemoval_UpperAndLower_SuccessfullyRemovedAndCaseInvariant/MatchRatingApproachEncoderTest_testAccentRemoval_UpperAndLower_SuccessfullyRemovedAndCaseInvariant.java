package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link MatchRatingApproachEncoder#removeAccents(String)} strips accent
 * marks from both upper- and lower-case letters while leaving the original letter case
 * untouched.
 */
public class MatchRatingApproachEncoderTest_testAccentRemoval_UpperAndLower_SuccessfullyRemovedAndCaseInvariant extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    @Test
    final void testAccentRemoval_UpperAndLower_SuccessfullyRemovedAndCaseInvariant() {
        // Mixed-case accented input: "Á e í Ó u u" (the last two letters are unaccented).
        final String accentedInput = "ÁeíÓuu";

        // Each accented letter maps to its plain ASCII equivalent, and the case is preserved:
        // Á -> A, e -> e, í -> i, Ó -> O, u -> u, u -> u.
        final String expectedPlainAscii = "AeiOuu";

        assertEquals(expectedPlainAscii, getStringEncoder().removeAccents(accentedInput));
    }
}
