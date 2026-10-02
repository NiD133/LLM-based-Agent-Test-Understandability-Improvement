package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Tests that {@link MatchRatingApproachEncoder#isEncodeEquals(String, String)}
 * treats two phonetically dissimilar names as not matching.
 */
public class MatchRatingApproachEncoderTest_testCompare_KARL_ALESSANDRO_DoesNotMatch
        extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /**
     * "Karl" and "Alessandro" are phonetically unrelated, so the Match Rating
     * Approach must report that they are not homophones.
     */
    @Test
    final void encodeEqualsIsFalseForUnrelatedNames() {
        final MatchRatingApproachEncoder encoder = getStringEncoder();

        final boolean namesMatch = encoder.isEncodeEquals("Karl", "Alessandro");

        assertFalse(namesMatch, "'Karl' and 'Alessandro' should not be considered a phonetic match");
    }
}
