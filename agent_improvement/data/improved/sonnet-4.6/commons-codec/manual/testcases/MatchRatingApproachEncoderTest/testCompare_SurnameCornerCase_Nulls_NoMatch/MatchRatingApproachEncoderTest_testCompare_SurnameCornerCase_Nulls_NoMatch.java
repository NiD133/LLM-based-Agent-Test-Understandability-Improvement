package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertFalse;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testCompare_SurnameCornerCase_Nulls_NoMatch extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    @Test
    @DisplayName("isEncodeEquals returns false when both name arguments are null")
    final void testCompare_SurnameCornerCase_Nulls_NoMatch() {
        MatchRatingApproachEncoder encoder = getStringEncoder();
        // Null inputs cannot be compared phonetically; the algorithm treats them as non-matching.
        assertFalse(encoder.isEncodeEquals(null, null));
    }
}
