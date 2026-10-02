package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link MatchRatingApproachEncoder#isEncodeEquals(String, String)}
 * treats two phonetically dissimilar surnames as a non-match.
 */
public class MatchRatingApproachEncoderTest_testCompare_Surnames_MURPHY_LYNCH_NoMatchExpected
        extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    @Test
    final void testCompare_Surnames_MURPHY_LYNCH_NoMatchExpected() {
        // "Murphy" and "Lynch" are not homophones, so the encoder must report no match.
        final String firstSurname = "Murphy";
        final String secondSurname = "Lynch";

        final boolean surnamesMatch =
                getStringEncoder().isEncodeEquals(firstSurname, secondSurname);

        assertFalse(surnamesMatch, "Murphy and Lynch should not be considered a phonetic match");
    }
}
