package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link MatchRatingApproachEncoder#isEncodeEquals(String, String)} treats two names
 * as a phonetic match when the Match Rating Approach algorithm considers them homophonous.
 */
public class MatchRatingApproachEncoderTest_testCompare_TOMASZ_TOM_SuccessfullyMatched
        extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /**
     * "Tomasz" and "tom" should be reported as a match: the comparison is case-insensitive and the
     * Match Rating Approach encodings of the two names are similar enough to clear the minimum rating.
     */
    @Test
    final void isEncodeEquals_withTomaszAndTom_reportsPhoneticMatch() {
        final MatchRatingApproachEncoder encoder = getStringEncoder();

        final boolean namesMatch = encoder.isEncodeEquals("Tomasz", "tom");

        assertTrue(namesMatch, "'Tomasz' and 'tom' should be considered a phonetic match");
    }
}
