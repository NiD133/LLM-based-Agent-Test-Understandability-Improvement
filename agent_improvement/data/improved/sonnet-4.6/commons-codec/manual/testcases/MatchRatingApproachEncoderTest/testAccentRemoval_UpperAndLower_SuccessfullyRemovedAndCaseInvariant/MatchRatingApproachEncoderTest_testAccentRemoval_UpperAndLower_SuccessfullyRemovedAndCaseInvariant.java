package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testAccentRemoval_UpperAndLower_SuccessfullyRemovedAndCaseInvariant extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /**
     * Verifies that accented characters (both uppercase and lowercase) are replaced
     * with their plain ASCII equivalents while preserving the original letter case.
     * Input "ÁeíÓuu" contains: Á (uppercase A-acute), í (lowercase i-acute), Ó (uppercase O-acute).
     * Expected "AeiOuu": each accented letter is de-accented, unaccented letters are unchanged.
     */
    @Test
    final void testAccentRemoval_UpperAndLower_SuccessfullyRemovedAndCaseInvariant() {
        final String accentedInput  = "ÁeíÓuu";
        final String expectedOutput = "AeiOuu";

        assertEquals(expectedOutput, getStringEncoder().removeAccents(accentedInput),
                "removeAccents should strip acute accents from both upper- and lower-case letters "
                + "while preserving the original case of every character");
    }
}
