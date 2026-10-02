package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies the corner-case behaviour of {@link MatchRatingApproachEncoder#isEncodeEquals(String, String)}
 * when one of the names being compared is the empty string.
 */
public class MatchRatingApproachEncoderTest_testCompare_SurnamesCornerCase_MURPHY_NoSpace_NoMatch
        extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /**
     * A real surname can never be a phonetic match for the empty string: {@code isEncodeEquals}
     * short-circuits to {@code false} as soon as either argument is empty.
     */
    @Test
    final void surnameDoesNotMatchEmptyString() {
        final MatchRatingApproachEncoder encoder = getStringEncoder();

        final boolean namesAreHomophonous = encoder.isEncodeEquals("Murphy", "");

        assertFalse(namesAreHomophonous, "A non-empty surname must not match the empty string");
    }
}
