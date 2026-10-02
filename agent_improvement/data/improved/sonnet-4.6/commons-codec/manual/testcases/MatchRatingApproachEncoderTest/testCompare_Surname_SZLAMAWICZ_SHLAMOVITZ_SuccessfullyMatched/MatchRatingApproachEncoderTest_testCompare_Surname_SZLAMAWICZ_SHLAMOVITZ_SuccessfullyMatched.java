package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testCompare_Surname_SZLAMAWICZ_SHLAMOVITZ_SuccessfullyMatched extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /**
     * Verifies that the MRA algorithm recognises "SZLAMAWICZ" and "SHLAMOVITZ" as phonetically
     * equivalent surnames. Both are transliterations of the same Eastern-European name, so their
     * MRA codes should match despite the different spellings.
     */
    @Test
    final void testCompare_Surname_SZLAMAWICZ_SHLAMOVITZ_SuccessfullyMatched() {
        String surname1 = "SZLAMAWICZ";
        String surname2 = "SHLAMOVITZ";

        boolean phoneticallyEquivalent = getStringEncoder().isEncodeEquals(surname1, surname2);

        assertTrue(phoneticallyEquivalent,
                "Expected MRA to consider '" + surname1 + "' and '" + surname2 + "' as phonetically equivalent");
    }
}
