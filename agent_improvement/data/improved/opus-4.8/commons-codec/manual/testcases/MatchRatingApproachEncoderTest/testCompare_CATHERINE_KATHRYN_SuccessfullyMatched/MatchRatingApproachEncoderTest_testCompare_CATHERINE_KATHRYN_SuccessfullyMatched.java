package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link MatchRatingApproachEncoder#isEncodeEquals(String, String)}
 * treats two phonetically similar names as a match.
 *
 * <p>"Catherine" and "Kathryn" are different spellings of the same name and are
 * expected to be recognized as homophones by the Match Rating Approach algorithm.</p>
 */
public class MatchRatingApproachEncoderTest_testCompare_CATHERINE_KATHRYN_SuccessfullyMatched
        extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    @Test
    final void testCompare_CATHERINE_KATHRYN_SuccessfullyMatched() {
        final String firstName = "Catherine";
        final String phoneticallySimilarName = "Kathryn";

        final boolean namesAreMatched =
                getStringEncoder().isEncodeEquals(firstName, phoneticallySimilarName);

        assertTrue(namesAreMatched,
                "'Catherine' and 'Kathryn' should be recognized as a phonetic match");
    }
}
