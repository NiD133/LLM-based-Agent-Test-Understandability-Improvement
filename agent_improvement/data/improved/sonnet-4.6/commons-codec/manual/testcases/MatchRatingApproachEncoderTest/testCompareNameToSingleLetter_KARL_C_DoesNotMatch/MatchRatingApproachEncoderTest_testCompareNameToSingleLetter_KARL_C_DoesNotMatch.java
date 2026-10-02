package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertFalse;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testCompareNameToSingleLetter_KARL_C_DoesNotMatch extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /**
     * The MRA algorithm immediately returns false when either name is a single letter,
     * so "Karl" vs "C" must not be considered a match.
     */
    @Test
    final void testCompareNameToSingleLetter_KARL_C_DoesNotMatch() {
        MatchRatingApproachEncoder encoder = getStringEncoder();
        assertFalse(encoder.isEncodeEquals("Karl", "C"));
    }
}
