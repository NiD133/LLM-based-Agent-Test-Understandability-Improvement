package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link MatchRatingApproachEncoder#removeDoubleConsonants(String)}.
 */
public class MatchRatingApproachEncoderTest_testRemoveSingleDoubleConsonants_BUBLE_RemovedSuccessfully
        extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /**
     * Verifies that {@code removeDoubleConsonants} collapses a double consonant
     * to a single one: the "BB" pair in "BUBBLE" is reduced so the result is "BUBLE".
     */
    @Test
    final void testRemoveSingleDoubleConsonants_BUBLE_RemovedSuccessfully() {
        final String inputWithDoubleConsonant = "BUBBLE";
        final String expectedWithSingleConsonant = "BUBLE";

        final String actual = getStringEncoder().removeDoubleConsonants(inputWithDoubleConsonant);

        assertEquals(expectedWithSingleConsonant, actual);
    }
}
