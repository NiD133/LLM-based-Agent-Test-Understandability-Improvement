package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.EncoderException;
import org.junit.jupiter.api.Test;

public class HexTest_testEncodeDecodeHexCharArrayRandom {

    @Test
    void testEncodeDecodeHexCharArrayRandom() throws DecoderException, EncoderException {
        final Hex hex = new Hex();
        for (int i = 5; i > 0; i--) {
            final byte[] data = new byte[ThreadLocalRandom.current().nextInt(10000) + 1];
            ThreadLocalRandom.current().nextBytes(data);

            // Static char[] API: bytes -> hexadecimal characters -> bytes.
            final char[] encodedChars = Hex.encodeHex(data);
            byte[] decodedBytes = Hex.decodeHex(encodedChars);
            assertArrayEquals(data, decodedBytes);

            // Instance byte[] API: bytes -> charset-backed hexadecimal bytes -> bytes.
            final byte[] encodedStringBytes = hex.encode(data);
            decodedBytes = hex.decode(encodedStringBytes);
            assertArrayEquals(data, decodedBytes);

            // Instance Object API with a String input and char[] decode input.
            String dataString = new String(encodedChars);
            char[] encodedStringChars = (char[]) hex.encode(dataString);
            decodedBytes = (byte[]) hex.decode(encodedStringChars);
            assertArrayEquals(StringUtils.getBytesUtf8(dataString), decodedBytes);

            // Instance Object API with a String input and String decode input.
            dataString = new String(encodedChars);
            encodedStringChars = (char[]) hex.encode(dataString);
            decodedBytes = (byte[]) hex.decode(new String(encodedStringChars));
            assertArrayEquals(StringUtils.getBytesUtf8(dataString), decodedBytes);
        }
    }
}
