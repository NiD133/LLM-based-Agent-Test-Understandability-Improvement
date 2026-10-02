package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testIsEncodeEquals_CornerCase_FirstNameNull_ReturnsFalse {

    private MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    @Test
    final void testIsEncodeEquals_CornerCase_FirstNameNull_ReturnsFalse() {
        final boolean namesMatch = createStringEncoder().isEncodeEquals(null, "test");

        assertFalse(namesMatch);
    }
}
