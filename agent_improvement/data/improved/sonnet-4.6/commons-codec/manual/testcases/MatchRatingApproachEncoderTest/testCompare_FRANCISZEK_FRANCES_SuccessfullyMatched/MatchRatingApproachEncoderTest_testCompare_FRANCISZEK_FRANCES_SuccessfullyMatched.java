package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testCompare_FRANCISZEK_FRANCES_SuccessfullyMatched extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /**
     * "Franciszek" (Polish) and "Frances" (English) are cross-language variants of the same
     * given name. After MRA encoding both reduce to phonetically equivalent codes, so
     * isEncodeEquals must return true.
     */
    @Test
    @DisplayName("isEncodeEquals: 'Franciszek' (Polish) matches 'Frances' (English) as phonetic equivalents")
    final void testCompare_FRANCISZEK_FRANCES_SuccessfullyMatched() {
        assertTrue(getStringEncoder().isEncodeEquals("Franciszek", "Frances"));
    }
}
