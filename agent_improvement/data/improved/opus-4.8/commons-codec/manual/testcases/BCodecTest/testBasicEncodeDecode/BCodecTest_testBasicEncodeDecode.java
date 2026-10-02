package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link BCodec} performs a basic round trip: encoding a plain
 * ASCII string into its RFC 1522 "B" (Base64) encoded-word form and decoding
 * that form back to the original string.
 */
public class BCodecTest_testBasicEncodeDecode {

    @Test
    void testBasicEncodeDecode() throws Exception {
        final BCodec bcodec = new BCodec();

        final String plainText = "Hello there";
        // RFC 1522 encoded-word: =?<charset>?<encoding>?<encoded-bytes>?=
        // BCodec defaults to UTF-8 and uses "B" (Base64) encoding.
        final String expectedEncoded = "=?UTF-8?B?SGVsbG8gdGhlcmU=?=";

        final String actualEncoded = bcodec.encode(plainText);
        assertEquals(expectedEncoded, actualEncoded, "Basic B encoding test");

        final String decoded = bcodec.decode(actualEncoded);
        assertEquals(plainText, decoded, "Basic B decoding test");
    }
}
