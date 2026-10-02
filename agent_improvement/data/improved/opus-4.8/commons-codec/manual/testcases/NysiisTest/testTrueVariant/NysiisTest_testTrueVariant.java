package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Tests the strict ("true") variant of the {@link Nysiis} encoder, which caps the
 * encoded output at a maximum length of 6 characters.
 */
public class NysiisTest_testTrueVariant extends AbstractStringEncoderTest<Nysiis> {

    @Override
    protected Nysiis createStringEncoder() {
        return new Nysiis();
    }

    @Test
    void testTrueVariant() {
        // strict == true caps the NYSIIS code at 6 characters.
        final Nysiis strictEncoder = new Nysiis(true);

        final String encoded = strictEncoder.encode("WESTERLUND");

        assertTrue(encoded.length() <= 6, "Strict variant must not exceed 6 characters");
        assertEquals("WASTAR", encoded, "Unexpected NYSIIS encoding for WESTERLUND");
    }
}
