package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link MatchRatingApproachEncoder#removeDoubleConsonants(String)}.
 */
public class MatchRatingApproachEncoderTest_testRemoveDoubleConsonants_MISSISSIPPI_RemovedSuccessfully
        extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /**
     * Verifies that each doubled consonant in a word is collapsed to a single
     * consonant. In "MISSISSIPPI" the pairs "SS", "SS" and "PP" are each reduced
     * to one letter, yielding "MISISIPI".
     */
    @Test
    final void testRemoveDoubleConsonants_MISSISSIPPI_RemovedSuccessfully() {
        final String wordWithDoubleConsonants = "MISSISSIPPI";
        final String expectedSingleConsonants = "MISISIPI";

        final String actual = getStringEncoder().removeDoubleConsonants(wordWithDoubleConsonants);

        assertEquals(expectedSingleConsonants, actual);
    }
}
