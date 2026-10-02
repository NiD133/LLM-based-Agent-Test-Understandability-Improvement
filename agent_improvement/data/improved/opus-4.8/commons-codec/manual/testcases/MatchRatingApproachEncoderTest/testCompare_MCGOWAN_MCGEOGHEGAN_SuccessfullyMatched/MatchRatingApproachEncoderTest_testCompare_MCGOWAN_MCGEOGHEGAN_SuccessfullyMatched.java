package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link MatchRatingApproachEncoder#isEncodeEquals(String, String)} treats two
 * phonetically similar surnames as a match.
 */
public class MatchRatingApproachEncoderTest_testCompare_MCGOWAN_MCGEOGHEGAN_SuccessfullyMatched
        extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /**
     * "McGowan" and "Mc Geoghegan" encode to a similar enough Match Rating Approach code that the
     * algorithm considers them homophonous, so {@code isEncodeEquals} should return {@code true}.
     */
    @Test
    final void mcGowanAndMcGeogheganAreConsideredAMatch() {
        final MatchRatingApproachEncoder encoder = getStringEncoder();

        final boolean namesMatch = encoder.isEncodeEquals("McGowan", "Mc Geoghegan");

        assertTrue(namesMatch, "McGowan and Mc Geoghegan should be matched as homophones");
    }
}
