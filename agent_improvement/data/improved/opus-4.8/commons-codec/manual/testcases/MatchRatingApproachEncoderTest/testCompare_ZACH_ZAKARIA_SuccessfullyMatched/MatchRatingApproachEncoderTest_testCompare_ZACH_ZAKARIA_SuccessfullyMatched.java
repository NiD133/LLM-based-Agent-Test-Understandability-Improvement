package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link MatchRatingApproachEncoder} treats two phonetically
 * similar names as a match.
 */
public class MatchRatingApproachEncoderTest_testCompare_ZACH_ZAKARIA_SuccessfullyMatched
        extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /**
     * "Zach" and "Zacharia" sound alike, so the Match Rating Approach algorithm
     * should report them as homophones (an encode match).
     */
    @Test
    final void testCompare_ZACH_ZAKARIA_SuccessfullyMatched() {
        final String firstName = "Zach";
        final String similarSoundingName = "Zacharia";

        final boolean namesMatch =
                getStringEncoder().isEncodeEquals(firstName, similarSoundingName);

        assertTrue(namesMatch,
                "'" + firstName + "' and '" + similarSoundingName
                        + "' should be recognized as a phonetic match");
    }
}
