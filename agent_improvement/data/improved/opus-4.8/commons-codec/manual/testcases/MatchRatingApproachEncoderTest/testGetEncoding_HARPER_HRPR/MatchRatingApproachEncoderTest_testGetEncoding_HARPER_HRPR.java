package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link MatchRatingApproachEncoder} produces the expected
 * Match Rating Approach (MRA) code for a single sample name.
 *
 * <p>The MRA encoding of "HARPER" is derived as follows:</p>
 * <ol>
 *   <li>Remove vowels (keeping a leading vowel): {@code HARPER -> HRPR}</li>
 *   <li>No double consonants to collapse, and the result is &le; 6 characters,
 *       so it is returned unchanged: {@code HRPR}</li>
 * </ol>
 */
public class MatchRatingApproachEncoderTest_testGetEncoding_HARPER_HRPR
        extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    @Test
    final void encodesHarperToHrpr() {
        final String nameToEncode = "HARPER";
        final String expectedMraCode = "HRPR";

        final String actualMraCode = getStringEncoder().encode(nameToEncode);

        assertEquals(expectedMraCode, actualMraCode,
                "MRA encoding of \"" + nameToEncode + "\" should be \"" + expectedMraCode + "\"");
    }
}
