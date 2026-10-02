package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link MatchRatingApproachEncoder#isEncodeEquals(String, String)}.
 *
 * <p>The Match Rating Approach is a phonetic algorithm: two names are considered
 * a match when they sound alike, even if they are spelled differently.</p>
 */
public class MatchRatingApproachEncoderTest_testCompare_OONA_OONAGH_SuccessfullyMatched
        extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /**
     * "Oona" and "Oonagh" are spelled differently but are phonetically equivalent,
     * so the encoder should report them as a match.
     */
    @Test
    final void testCompare_OONA_OONAGH_SuccessfullyMatched() {
        final MatchRatingApproachEncoder encoder = getStringEncoder();

        final boolean namesAreHomophones = encoder.isEncodeEquals("Oona", "Oonagh");

        assertTrue(namesAreHomophones, "'Oona' and 'Oonagh' should be matched as phonetically equal");
    }
}
