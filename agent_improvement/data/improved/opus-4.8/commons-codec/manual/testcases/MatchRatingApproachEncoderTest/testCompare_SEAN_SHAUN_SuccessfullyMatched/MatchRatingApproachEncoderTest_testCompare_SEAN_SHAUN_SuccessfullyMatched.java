package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link MatchRatingApproachEncoder#isEncodeEquals(String, String)}
 * treats two phonetically similar names as a match.
 */
public class MatchRatingApproachEncoderTest_testCompare_SEAN_SHAUN_SuccessfullyMatched
        extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /**
     * "Séan" and "Shaun" sound alike, so the Match Rating Approach should report
     * them as equal once accents are stripped and the algorithm is applied.
     */
    @Test
    final void testCompare_SEAN_SHAUN_SuccessfullyMatched() {
        final MatchRatingApproachEncoder encoder = getStringEncoder();

        final boolean namesAreHomophones = encoder.isEncodeEquals("Séan", "Shaun");

        assertTrue(namesAreHomophones, "'Séan' and 'Shaun' should be matched as homophones");
    }
}
