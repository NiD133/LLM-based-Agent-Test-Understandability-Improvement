package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testCompare_STEPHEN_STEFAN_SuccessfullyMatched {

    private MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    @Test
    final void testCompare_STEPHEN_STEFAN_SuccessfullyMatched() {
        final String canonicalSpelling = "Stephen";
        final String phoneticVariant = "Stefan";

        final boolean namesAreMatched = createStringEncoder().isEncodeEquals(canonicalSpelling, phoneticVariant);

        assertTrue(namesAreMatched);
    }
}
