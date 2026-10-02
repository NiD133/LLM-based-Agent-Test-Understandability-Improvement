package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies the basic US-English Soundex encoding produced by {@link Soundex#encode(String)}.
 *
 * <p>The inputs below are the words of the pangram "The quick brown fox jumped over the lazy
 * dogs". Each word is paired with the four-character Soundex code it is expected to produce.
 * A Soundex code keeps the first letter of the word and then encodes up to three following
 * consonant sounds as digits, padding with zeros.</p>
 */
public class SoundexTest_testEncodeBasic extends AbstractStringEncoderTest<Soundex> {

    @Override
    protected Soundex createStringEncoder() {
        return new Soundex();
    }

    @Test
    void testEncodeBasic() {
        final Soundex soundex = getStringEncoder();

        assertEquals("T235", soundex.encode("testing"));
        assertEquals("T000", soundex.encode("The"));
        assertEquals("Q200", soundex.encode("quick"));
        assertEquals("B650", soundex.encode("brown"));
        assertEquals("F200", soundex.encode("fox"));
        assertEquals("J513", soundex.encode("jumped"));
        assertEquals("O160", soundex.encode("over"));
        assertEquals("T000", soundex.encode("the"));
        assertEquals("L200", soundex.encode("lazy"));
        assertEquals("D200", soundex.encode("dogs"));
    }
}
