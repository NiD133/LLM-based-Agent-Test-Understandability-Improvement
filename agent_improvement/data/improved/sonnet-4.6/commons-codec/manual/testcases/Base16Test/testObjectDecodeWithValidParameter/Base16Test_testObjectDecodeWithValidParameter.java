package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

public class Base16Test_testObjectDecodeWithValidParameter {

    @Test
    void testObjectDecodeWithValidParameter() throws Exception {
        // Encode a plain-text string into its Base16 representation
        final String originalText = "Hello World!";
        final Object encodedBytes = new Base16().encode(originalText.getBytes(StandardCharsets.UTF_8));

        // Decode the Base16 bytes back through the Object-typed API
        final Base16 base16Decoder = new Base16();
        final Object decodedObject = base16Decoder.decode(encodedBytes);

        // Cast to byte[] and reconstruct the string, expecting the original value
        final byte[] decodedBytes = (byte[]) decodedObject;
        final String decodedText = new String(decodedBytes);
        assertEquals(originalText, decodedText, "dest string does not equal original");
    }
}
