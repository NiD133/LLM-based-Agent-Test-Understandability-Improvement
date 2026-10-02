package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.apache.commons.codec.EncoderException;
import org.junit.jupiter.api.Test;

public class SoundexTest_testNewInstance3 extends AbstractStringEncoderTest<Soundex> {

    @Override
    protected Soundex createStringEncoder() {
        return new Soundex();
    }

    /**
     * Verifies that a Soundex instance constructed from the US_ENGLISH_MAPPING_STRING
     * produces the same encoding as the default constructor for a typical name.
     * "Williams" -> "W452": W (kept as first letter), l->4, m->5, s->2.
     */
    @Test
    void testNewInstance3() {
        // Construct Soundex using the String-based constructor with the standard US English mapping
        Soundex soundexFromMappingString = new Soundex(Soundex.US_ENGLISH_MAPPING_STRING);

        String encodedWilliams = soundexFromMappingString.soundex("Williams");

        assertEquals("W452", encodedWilliams);
    }
}
