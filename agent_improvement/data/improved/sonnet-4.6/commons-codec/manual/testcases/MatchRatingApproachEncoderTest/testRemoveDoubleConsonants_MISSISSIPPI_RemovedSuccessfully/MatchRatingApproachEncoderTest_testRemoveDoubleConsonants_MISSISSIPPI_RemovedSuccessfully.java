package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testRemoveDoubleConsonants_MISSISSIPPI_RemovedSuccessfully extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /**
     * MISSISSIPPI contains three types of double consonants: SS (twice), PP, and LL — wait, no LL.
     * Concretely: M-I-SS-I-SS-I-PP-I → each doubled consonant pair collapses to one letter → MISISIPI.
     */
    @Test
    final void testRemoveDoubleConsonants_MISSISSIPPI_RemovedSuccessfully() {
        String input = "MISSISSIPPI";
        String expectedOutput = "MISISIPI"; // SS→S (×2), PP→P: M-I-S-I-S-I-P-I

        assertEquals(expectedOutput, getStringEncoder().removeDoubleConsonants(input));
    }
}
