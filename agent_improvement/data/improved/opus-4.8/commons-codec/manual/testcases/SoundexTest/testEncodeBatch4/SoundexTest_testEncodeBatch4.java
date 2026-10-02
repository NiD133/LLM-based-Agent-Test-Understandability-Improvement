package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies the default US-English {@link Soundex} encoding against a batch of
 * known name/code pairs.
 *
 * <p>The expected codes are taken from the worked examples published at
 * http://www.myatt.demon.co.uk/sxalg.htm</p>
 */
public class SoundexTest_testEncodeBatch4 extends AbstractStringEncoderTest<Soundex> {

    @Override
    protected Soundex createStringEncoder() {
        return new Soundex();
    }

    /**
     * Asserts that the given name encodes to the given Soundex code using the
     * default encoder under test.
     *
     * @param name         the name to encode.
     * @param expectedCode the Soundex code the name should produce.
     */
    private void assertSoundex(final String name, final String expectedCode) {
        assertEquals(expectedCode, getStringEncoder().encode(name),
                "Soundex code for \"" + name + "\"");
    }

    @Test
    void testEncodeBatch4() {
        assertSoundex("HOLMES", "H452");
        assertSoundex("ADOMOMI", "A355");
        assertSoundex("VONDERLEHR", "V536");
        assertSoundex("BALL", "B400");
        assertSoundex("SHAW", "S000");
        assertSoundex("JACKSON", "J250");
        assertSoundex("SCANLON", "S545");
        assertSoundex("SAINTJOHN", "S532");
    }
}
