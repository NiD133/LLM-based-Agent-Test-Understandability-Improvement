package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.EncoderException;
import org.junit.jupiter.api.Test;

/**
 * Tests that URLCodec throws the appropriate checked exceptions when constructed
 * with an unrecognized (unsupported) charset name.
 */
public class URLCodecTest_testInvalidEncoding {

    /**
     * A URLCodec initialized with a bogus charset name should fail on both encode and decode,
     * because the JVM cannot look up the charset and the codec wraps that failure in the
     * codec-specific checked exception type.
     */
    @Test
    void testInvalidEncoding() {
        final URLCodec urlCodec = new URLCodec("NONSENSE");
        final String plain = "Hello there!";

        // encode(String) internally calls getBytes(charset); an unknown charset name
        // causes UnsupportedEncodingException, which URLCodec re-throws as EncoderException.
        assertThrows(EncoderException.class, () -> urlCodec.encode(plain),
                "encode() must throw EncoderException for an unsupported charset");

        // decode(String) internally calls new String(bytes, charset); the same unknown
        // charset name causes UnsupportedEncodingException, re-thrown as DecoderException.
        assertThrows(DecoderException.class, () -> urlCodec.decode(plain),
                "decode() must throw DecoderException for an unsupported charset");
    }
}
