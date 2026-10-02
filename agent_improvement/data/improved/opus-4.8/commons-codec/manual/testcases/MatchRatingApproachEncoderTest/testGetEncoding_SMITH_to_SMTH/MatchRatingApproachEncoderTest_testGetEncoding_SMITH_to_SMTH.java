package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link MatchRatingApproachEncoder} reduces the name "Smith" to its
 * Match Rating Approach code "SMTH".
 *
 * <p>The encoder upper-cases the input and then removes vowels (except a leading one),
 * so "Smith" becomes "SMITH" and, after dropping the interior vowel "I", "SMTH".</p>
 */
public class MatchRatingApproachEncoderTest_testGetEncoding_SMITH_to_SMTH
        extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    @Test
    final void encodesSmithToSMTH() {
        final String inputName = "Smith";
        final String expectedCode = "SMTH";

        final String actualCode = getStringEncoder().encode(inputName);

        assertEquals(expectedCode, actualCode,
                "Match Rating Approach code for \"Smith\" should drop the interior vowel");
    }
}
