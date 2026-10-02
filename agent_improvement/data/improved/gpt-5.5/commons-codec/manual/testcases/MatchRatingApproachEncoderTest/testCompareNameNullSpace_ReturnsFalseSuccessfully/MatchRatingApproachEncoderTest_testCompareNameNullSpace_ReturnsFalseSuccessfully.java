package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testCompareNameNullSpace_ReturnsFalseSuccessfully {

    private final MatchRatingApproachEncoder stringEncoder = createStringEncoder();

    private MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    private MatchRatingApproachEncoder getStringEncoder() {
        return stringEncoder;
    }

    @Test
    final void testCompareNameNullSpace_ReturnsFalseSuccessfully() {
        final boolean namesMatch = getStringEncoder().isEncodeEquals(null, " ");

        assertFalse(namesMatch);
    }
}
