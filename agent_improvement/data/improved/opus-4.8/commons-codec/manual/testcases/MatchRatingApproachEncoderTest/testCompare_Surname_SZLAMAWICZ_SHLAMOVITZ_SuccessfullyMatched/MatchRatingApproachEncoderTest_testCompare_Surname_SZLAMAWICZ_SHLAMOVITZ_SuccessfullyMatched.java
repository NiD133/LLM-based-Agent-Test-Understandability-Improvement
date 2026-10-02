package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link MatchRatingApproachEncoder#isEncodeEquals(String, String)}.
 *
 * <p>The Match Rating Approach algorithm is meant to flag two names as
 * homophonous (sounding alike) even when they are spelled differently. This
 * test verifies that the two surname spellings "SZLAMAWICZ" and "SHLAMOVITZ"
 * are recognized as a match.</p>
 */
public class MatchRatingApproachEncoderTest_testCompare_Surname_SZLAMAWICZ_SHLAMOVITZ_SuccessfullyMatched
        extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    @Test
    final void testCompare_Surname_SZLAMAWICZ_SHLAMOVITZ_SuccessfullyMatched() {
        final String firstSpelling = "SZLAMAWICZ";
        final String secondSpelling = "SHLAMOVITZ";

        final boolean namesAreHomophonous =
                getStringEncoder().isEncodeEquals(firstSpelling, secondSpelling);

        assertTrue(namesAreHomophonous,
                "Differently spelled but similar-sounding surnames should be treated as a match");
    }
}
