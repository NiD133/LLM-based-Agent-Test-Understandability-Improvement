package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link MatchRatingApproachEncoder#isEncodeEquals(String, String)}
 * treats a regular name and a single-letter name as a non-match.
 *
 * <p>The Match Rating Approach algorithm short-circuits and returns {@code false}
 * whenever either argument is only one character long, so comparing "Karl"
 * against "C" must never be reported as a match.</p>
 */
public class MatchRatingApproachEncoderTest_testCompareNameToSingleLetter_KARL_C_DoesNotMatch
        extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    @Test
    final void testCompareNameToSingleLetter_KARL_C_DoesNotMatch() {
        final String name = "Karl";
        final String singleLetter = "C";

        // A single-letter operand can never be homophonous with a full name.
        final boolean namesMatch = getStringEncoder().isEncodeEquals(name, singleLetter);

        assertFalse(namesMatch, "A full name must not match a single-letter name");
    }
}
