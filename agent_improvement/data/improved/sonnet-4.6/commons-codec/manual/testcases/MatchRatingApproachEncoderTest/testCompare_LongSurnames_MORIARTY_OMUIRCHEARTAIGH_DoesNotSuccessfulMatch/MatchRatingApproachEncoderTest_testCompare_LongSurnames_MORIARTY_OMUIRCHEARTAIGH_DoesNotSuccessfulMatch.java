package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertFalse;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testCompare_LongSurnames_MORIARTY_OMUIRCHEARTAIGH_DoesNotSuccessfulMatch extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /**
     * Verifies that "Moriarty" and the long Irish surname "OMuircheartaigh" are not considered
     * phonetically equivalent by the MRA algorithm. Despite both names beginning with an 'M'/'O'
     * sound, their MRA-encoded forms ("MRTY" and "OMRTGH") differ enough that the similarity
     * rating falls below the minimum threshold, so isEncodeEquals must return false.
     */
    @Test
    final void testCompare_LongSurnames_MORIARTY_OMUIRCHEARTAIGH_DoesNotSuccessfulMatch() {
        assertFalse(getStringEncoder().isEncodeEquals("Moriarty", "OMuircheartaigh"));
    }
}
