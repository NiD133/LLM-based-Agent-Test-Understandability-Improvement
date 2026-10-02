package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link MatchRatingApproachEncoder#isEncodeEquals(String, String)}
 * treats the phonetically similar names "Steven" and "Stefan" as a match.
 */
public class MatchRatingApproachEncoderTest_testCompare_STEVEN_STEFAN_SuccessfullyMatched
        extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    @Test
    final void testCompare_STEVEN_STEFAN_SuccessfullyMatched() {
        // "Steven" and "Stefan" sound alike, so the Match Rating Approach
        // algorithm should consider them equal encodings.
        final String firstName = "Steven";
        final String similarSoundingName = "Stefan";

        final boolean namesMatch =
                getStringEncoder().isEncodeEquals(firstName, similarSoundingName);

        assertTrue(namesMatch, "Expected 'Steven' and 'Stefan' to be recognised as a phonetic match");
    }
}
