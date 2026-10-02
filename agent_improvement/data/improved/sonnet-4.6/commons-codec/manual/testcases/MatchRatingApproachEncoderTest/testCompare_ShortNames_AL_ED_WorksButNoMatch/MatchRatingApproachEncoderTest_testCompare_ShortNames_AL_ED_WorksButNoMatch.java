package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertFalse;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testCompare_ShortNames_AL_ED_WorksButNoMatch extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /**
     * "Al" encodes to "AL" and "Ed" encodes to "ED". Both are 2-letter names, so their
     * combined length is 4, which requires a minimum similarity rating of 5. The
     * left-to-right / right-to-left comparison produces a rating of only 4 (no shared
     * characters in matching positions), so the algorithm completes without error but
     * correctly reports no phonetic match.
     */
    @Test
    final void testCompare_ShortNames_AL_ED_WorksButNoMatch() {
        assertFalse(
            getStringEncoder().isEncodeEquals("Al", "Ed"),
            "\"Al\" and \"Ed\" share no characters in matching positions; MRA similarity score (4) falls below the minimum rating (5) for names with a combined encoded length of 4"
        );
    }
}
