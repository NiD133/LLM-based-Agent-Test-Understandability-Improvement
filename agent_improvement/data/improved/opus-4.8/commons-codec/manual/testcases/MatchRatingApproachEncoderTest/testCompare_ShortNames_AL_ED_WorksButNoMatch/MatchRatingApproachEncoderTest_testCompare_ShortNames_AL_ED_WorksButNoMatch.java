package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies {@link MatchRatingApproachEncoder#isEncodeEquals(String, String)} for two short,
 * phonetically distinct names. Although both names are long enough for the algorithm to run
 * (each has more than one character), "Al" and "Ed" share no phonetic similarity, so they
 * must not be reported as a match.
 */
public class MatchRatingApproachEncoderTest_testCompare_ShortNames_AL_ED_WorksButNoMatch
        extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    @Test
    final void shortNamesAlAndEdAreNotConsideredAMatch() {
        final MatchRatingApproachEncoder encoder = getStringEncoder();

        final boolean namesMatch = encoder.isEncodeEquals("Al", "Ed");

        assertFalse(namesMatch, "\"Al\" and \"Ed\" are phonetically different and should not match");
    }
}
