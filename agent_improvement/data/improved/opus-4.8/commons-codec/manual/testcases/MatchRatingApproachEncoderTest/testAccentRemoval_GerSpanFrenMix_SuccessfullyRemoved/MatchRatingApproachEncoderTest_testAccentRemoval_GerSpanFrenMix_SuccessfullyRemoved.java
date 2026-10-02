package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link MatchRatingApproachEncoder#removeAccents(String)} strips the diacritics
 * from a mix of German, Spanish and French accented letters, replacing each with its plain ASCII
 * equivalent while preserving the original letter case.
 */
public class MatchRatingApproachEncoderTest_testAccentRemoval_GerSpanFrenMix_SuccessfullyRemoved
        extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    @Test
    final void testAccentRemoval_GerSpanFrenMix_SuccessfullyRemoved() {
        // Each accented character maps to its plain ASCII letter (case preserved),
        // while the German "ß" has no ASCII equivalent and is therefore left unchanged:
        //   ä->a  ë->e  ö->o  ü->u  ß->ß  Ä->A  Ë->E  Ö->O  Ü->U  ñ->n  Ñ->N  à->a
        final String accentedInput = "äëöüßÄËÖÜñÑà";
        final String expectedPlainAscii = "aeoußAEOUnNa";

        assertEquals(expectedPlainAscii, getStringEncoder().removeAccents(accentedInput));
    }
}
