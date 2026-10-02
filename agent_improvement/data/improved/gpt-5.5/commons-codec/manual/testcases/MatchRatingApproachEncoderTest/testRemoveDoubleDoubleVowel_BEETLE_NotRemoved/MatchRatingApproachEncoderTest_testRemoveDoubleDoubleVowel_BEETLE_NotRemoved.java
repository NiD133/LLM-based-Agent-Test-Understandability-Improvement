package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testRemoveDoubleDoubleVowel_BEETLE_NotRemoved {

    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    private MatchRatingApproachEncoder getStringEncoder() {
        return createStringEncoder();
    }

    @Test
    final void testRemoveDoubleDoubleVowel_BEETLE_NotRemoved() {
        final String wordWithDoubleVowel = "BEETLE";
        final String expectedUnchangedWord = "BEETLE";

        assertEquals(expectedUnchangedWord, getStringEncoder().removeDoubleConsonants(wordWithDoubleVowel));
    }
}
