package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testCompare_KARL_ALESSANDRO_DoesNotMatch {

    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    private MatchRatingApproachEncoder getStringEncoder() {
        return createStringEncoder();
    }

    @Test
    final void testCompare_KARL_ALESSANDRO_DoesNotMatch() {
        // Regression case: these two distinct names must not be treated as a phonetic match.
        assertFalse(getStringEncoder().isEncodeEquals("Karl", "Alessandro"));
    }
}
