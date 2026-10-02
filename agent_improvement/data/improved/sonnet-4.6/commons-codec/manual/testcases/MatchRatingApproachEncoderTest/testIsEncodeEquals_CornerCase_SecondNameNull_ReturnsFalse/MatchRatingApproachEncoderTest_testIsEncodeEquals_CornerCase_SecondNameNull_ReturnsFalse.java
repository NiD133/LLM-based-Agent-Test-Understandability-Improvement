package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertFalse;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testIsEncodeEquals_CornerCase_SecondNameNull_ReturnsFalse extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    @Test
    final void testIsEncodeEquals_CornerCase_SecondNameNull_ReturnsFalse() {
        // isEncodeEquals returns false immediately when either name is null
        MatchRatingApproachEncoder encoder = getStringEncoder();
        assertFalse(encoder.isEncodeEquals("test", null));
    }
}
