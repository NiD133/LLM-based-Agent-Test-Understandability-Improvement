package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link MatchRatingApproachEncoder#isEncodeEquals(String, String)} treats two long,
 * differently-spelled forms of the same Irish surname as a phonetic match.
 *
 * <p>The two inputs are anglicised/accented variants of the surname "Ó Muircheartaigh". Despite
 * differing in casing, accents, apostrophes and surrounding whitespace, the Match Rating Approach
 * algorithm should still consider them homophones.</p>
 */
public class MatchRatingApproachEncoderTest_testCompare_LongSurnames_OMUIRCHEARTAIGH_OMIREADHAIGH_SuccessfulMatch
        extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    @Test
    final void testCompare_LongSurnames_OMUIRCHEARTAIGH_OMIREADHAIGH_SuccessfulMatch() {
        // Two spelling variants of the same Irish surname, with different casing,
        // accents, apostrophe placement and trailing whitespace.
        final String surnameVariantOne = "o'muireadhaigh";
        final String surnameVariantTwo = "Ó 'Muircheartaigh ";

        final boolean namesAreHomophones =
                getStringEncoder().isEncodeEquals(surnameVariantOne, surnameVariantTwo);

        assertTrue(namesAreHomophones,
                "Expected the two surname variants to be recognised as a phonetic match");
    }
}
