package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testCompare_TOMASZ_TOM_SuccessfullyMatched extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /**
     * Verifies that the MRA algorithm considers "Tomasz" and "tom" phonetically equivalent.
     * "Tomasz" is a Polish given name whose pronunciation is close enough to the English
     * nickname "tom" that the Match Rating Approach similarity check should return true.
     */
    @Test
    final void testCompare_TOMASZ_TOM_SuccessfullyMatched() {
        String polishName = "Tomasz";
        String englishNickname = "tom";

        boolean phoneticallyMatched = getStringEncoder().isEncodeEquals(polishName, englishNickname);

        assertTrue(phoneticallyMatched,
                "Expected \"Tomasz\" and \"tom\" to be considered phonetically equivalent by the MRA algorithm");
    }
}
