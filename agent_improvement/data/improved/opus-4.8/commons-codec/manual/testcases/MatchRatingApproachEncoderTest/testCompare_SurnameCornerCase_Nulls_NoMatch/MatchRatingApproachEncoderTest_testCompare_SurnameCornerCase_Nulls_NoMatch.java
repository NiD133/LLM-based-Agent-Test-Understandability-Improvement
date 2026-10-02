package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Tests the corner case of {@link MatchRatingApproachEncoder#isEncodeEquals(String, String)}
 * where both surnames are {@code null}.
 *
 * <p>Two {@code null} inputs are treated as trivial (NINO) input and must never be reported
 * as a phonetic match, so the comparison is expected to return {@code false}.</p>
 */
public class MatchRatingApproachEncoderTest_testCompare_SurnameCornerCase_Nulls_NoMatch
        extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    @Test
    final void testCompare_SurnameCornerCase_Nulls_NoMatch() {
        final MatchRatingApproachEncoder encoder = getStringEncoder();

        final boolean bothNullNamesMatch = encoder.isEncodeEquals(null, null);

        assertFalse(bothNullNamesMatch, "Two null surnames must not be reported as a match");
    }
}
