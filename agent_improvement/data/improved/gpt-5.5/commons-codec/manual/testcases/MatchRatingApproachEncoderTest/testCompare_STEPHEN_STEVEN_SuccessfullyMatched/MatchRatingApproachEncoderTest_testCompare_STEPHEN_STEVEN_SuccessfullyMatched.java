package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testCompare_STEPHEN_STEVEN_SuccessfullyMatched {

    @Test
    final void testCompare_STEPHEN_STEVEN_SuccessfullyMatched() {
        final MatchRatingApproachEncoder encoder = new MatchRatingApproachEncoder();
        final boolean namesMatch = encoder.isEncodeEquals("Stephen", "Steven");

        assertTrue(namesMatch);
    }
}
