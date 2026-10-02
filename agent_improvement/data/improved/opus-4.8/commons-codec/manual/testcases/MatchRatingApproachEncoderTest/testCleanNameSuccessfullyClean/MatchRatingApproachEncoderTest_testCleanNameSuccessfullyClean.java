package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link MatchRatingApproachEncoder#cleanName(String)}.
 */
public class MatchRatingApproachEncoderTest_testCleanNameSuccessfullyClean extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /**
     * Verifies that {@code cleanName} normalizes a name by upper-casing it,
     * stripping punctuation (hyphen, period, comma, ampersand), removing accents
     * (the accented "í" becomes "I"), and deleting all whitespace.
     */
    @Test
    final void testCleanNameSuccessfullyClean() {
        final String messyName = "This-ís   a t.,es &t";
        final String expectedCleanName = "THISISATEST";

        final String actualCleanName = getStringEncoder().cleanName(messyName);

        assertEquals(expectedCleanName, actualCleanName);
    }
}
