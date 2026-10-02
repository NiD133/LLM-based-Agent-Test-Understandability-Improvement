package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testCompare_Surname_MOSKOWITZ_MOSKOVITZ_SuccessfullyMatched extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /**
     * "Moskowitz" and "Moskovitz" are common variant spellings of the same surname.
     * The Match Rating Approach algorithm should treat them as phonetically equivalent
     * because both encode to the same MRA code after vowel removal and consonant reduction.
     */
    @Test
    final void testCompare_Surname_MOSKOWITZ_MOSKOVITZ_SuccessfullyMatched() {
        String variantSpelling1 = "Moskowitz";
        String variantSpelling2 = "Moskovitz";

        boolean phoneticallyEquivalent = getStringEncoder().isEncodeEquals(variantSpelling1, variantSpelling2);

        assertTrue(phoneticallyEquivalent,
                "MRA encoder should match surname variants 'Moskowitz' and 'Moskovitz' as phonetically equivalent");
    }
}
