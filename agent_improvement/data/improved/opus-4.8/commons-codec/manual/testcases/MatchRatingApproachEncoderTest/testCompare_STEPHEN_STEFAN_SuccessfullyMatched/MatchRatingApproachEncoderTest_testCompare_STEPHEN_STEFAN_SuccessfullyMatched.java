package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link MatchRatingApproachEncoder#isEncodeEquals(String, String)} treats
 * the phonetically similar names "Stephen" and "Stefan" as a match.
 */
public class MatchRatingApproachEncoderTest_testCompare_STEPHEN_STEFAN_SuccessfullyMatched
        extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    @Test
    final void stephenAndStefanAreConsideredEqual() {
        final MatchRatingApproachEncoder encoder = getStringEncoder();

        final boolean namesMatch = encoder.isEncodeEquals("Stephen", "Stefan");

        assertTrue(namesMatch, "\"Stephen\" and \"Stefan\" should be encoded as homophones");
    }
}
