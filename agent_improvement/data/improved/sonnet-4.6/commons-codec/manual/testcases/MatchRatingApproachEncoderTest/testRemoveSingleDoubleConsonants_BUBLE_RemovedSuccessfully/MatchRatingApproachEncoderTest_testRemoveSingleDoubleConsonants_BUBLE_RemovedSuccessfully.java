package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testRemoveSingleDoubleConsonants_BUBLE_RemovedSuccessfully extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    @Test
    final void testRemoveSingleDoubleConsonants_BUBLE_RemovedSuccessfully() {
        String wordWithDoubleConsonant = "BUBBLE";
        String expectedWithSingleConsonant = "BUBLE";

        assertEquals(expectedWithSingleConsonant, getStringEncoder().removeDoubleConsonants(wordWithDoubleConsonant));
    }
}
