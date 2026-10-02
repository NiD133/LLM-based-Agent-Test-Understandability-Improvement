package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testRemoveSingleDoubleConsonants_BUBLE_RemovedSuccessfully {

    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    @Test
    final void testRemoveSingleDoubleConsonants_BUBLE_RemovedSuccessfully() {
        final String nameWithDoubleConsonant = "BUBBLE";
        final String expectedNameAfterRemovingDuplicateConsonant = "BUBLE";

        assertEquals(expectedNameAfterRemovingDuplicateConsonant,
                createStringEncoder().removeDoubleConsonants(nameWithDoubleConsonant));
    }
}
