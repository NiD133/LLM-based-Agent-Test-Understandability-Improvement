package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link MatchRatingApproachEncoder} treats the phonetically
 * similar names "smith" and "smyth" as a match.
 */
public class MatchRatingApproachEncoderTest_testCompare_SMITH_SMYTH_SuccessfullyMatched
        extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    @Test
    final void testCompare_SMITH_SMYTH_SuccessfullyMatched() {
        final MatchRatingApproachEncoder encoder = getStringEncoder();

        // "smith" and "smyth" sound alike, so the Match Rating Approach
        // algorithm should consider their encodings equal.
        final boolean namesMatch = encoder.isEncodeEquals("smith", "smyth");

        assertTrue(namesMatch, "Expected 'smith' and 'smyth' to be matched as homophones");
    }
}
