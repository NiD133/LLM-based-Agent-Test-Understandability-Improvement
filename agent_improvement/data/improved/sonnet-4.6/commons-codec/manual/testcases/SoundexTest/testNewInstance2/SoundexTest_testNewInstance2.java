package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.apache.commons.codec.EncoderException;
import org.junit.jupiter.api.Test;

public class SoundexTest_testNewInstance2 extends AbstractStringEncoderTest<Soundex> {

    @Override
    protected Soundex createStringEncoder() {
        return new Soundex();
    }

    /**
     * Verifies that constructing a Soundex instance via the char-array constructor
     * with the standard US-English mapping produces the correct Soundex code.
     * "Williams" -> "W452": W (first letter), 4 (L), 5 (M), 2 (S).
     */
    @Test
    void testNewInstance2() {
        char[] usEnglishMapping = Soundex.US_ENGLISH_MAPPING_STRING.toCharArray();
        Soundex soundex = new Soundex(usEnglishMapping);

        assertEquals("W452", soundex.soundex("Williams"));
    }
}
