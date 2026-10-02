package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.apache.commons.codec.EncoderException;
import org.junit.jupiter.api.Test;

public class SoundexTest_testDifference extends AbstractStringEncoderTest<Soundex> {

    @Override
    protected Soundex createStringEncoder() {
        return new Soundex();
    }

    /**
     * The difference score ranges from 0 (no phonetic similarity) to 4 (identical or nearly identical sound).
     * Null and blank inputs have no phonetic content, so they score 0 against each other.
     */
    @Test
    void testDifferenceWithNullAndBlankInputs() throws EncoderException {
        assertEquals(0, getStringEncoder().difference(null, null));
        assertEquals(0, getStringEncoder().difference("", ""));
        assertEquals(0, getStringEncoder().difference(" ", " "));
    }

    /**
     * Names that sound alike receive a high score; names with little phonetic overlap receive a low score.
     */
    @Test
    void testDifferenceWithVariousNamePairs() throws EncoderException {
        // "Smith" and "Smythe" share the same Soundex code → maximum similarity
        assertEquals(4, getStringEncoder().difference("Smith", "Smythe"));
        // "Ann" and "Andrew" share the first two encoded characters
        assertEquals(2, getStringEncoder().difference("Ann", "Andrew"));
        // "Margaret" and "Andrew" share only the first encoded character
        assertEquals(1, getStringEncoder().difference("Margaret", "Andrew"));
        // "Janet" and "Margaret" have completely different Soundex codes
        assertEquals(0, getStringEncoder().difference("Janet", "Margaret"));
    }

    /**
     * Examples taken from the Microsoft T-SQL DIFFERENCE documentation (de-dz reference page).
     * See: https://msdn.microsoft.com/library/default.asp?url=/library/en-us/tsqlref/ts_de-dz_8co5.asp
     */
    @Test
    void testDifferenceExamplesFromMsdnDeDz() throws EncoderException {
        // "Green" and "Greene" encode to the same Soundex value
        assertEquals(4, getStringEncoder().difference("Green", "Greene"));
        // "Blotchet-Halls" and "Greene" are phonetically unrelated
        assertEquals(0, getStringEncoder().difference("Blotchet-Halls", "Greene"));
    }

    /**
     * Examples taken from the Microsoft T-SQL DIFFERENCE documentation (setu-sus reference page).
     * See: https://msdn.microsoft.com/library/default.asp?url=/library/en-us/tsqlref/ts_setu-sus_3o6w.asp
     */
    @Test
    void testDifferenceExamplesFromMsdnSetuSus() throws EncoderException {
        // "Smith"/"Smythe" and "Smithers"/"Smythers" both encode identically under Soundex
        assertEquals(4, getStringEncoder().difference("Smith", "Smythe"));
        assertEquals(4, getStringEncoder().difference("Smithers", "Smythers"));
        // "Anothers" and "Brothers" share only the second Soundex digit
        assertEquals(2, getStringEncoder().difference("Anothers", "Brothers"));
    }
}
