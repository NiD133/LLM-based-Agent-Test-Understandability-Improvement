package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertFalse;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testIsEncodeEquals_CornerCase_SecondNameNothing_ReturnsFalse extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    // MRA algorithm requires both names to be non-empty; an empty second name
    // is rejected before any phonetic comparison takes place.
    @Test
    final void testIsEncodeEquals_CornerCase_SecondNameNothing_ReturnsFalse() {
        final String firstName = "test";
        final String emptySecondName = "";

        final boolean result = getStringEncoder().isEncodeEquals(firstName, emptySecondName);

        assertFalse(result);
    }
}
