package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.EncoderException;
import org.junit.jupiter.api.Test;

public class HexTest_testEncodeDecodeHexCharArrayRandom {

    private static final int ITERATIONS = 5;

    @Test
    void testEncodeDecodeHexCharArrayRandom() throws DecoderException, EncoderException {
        final Hex hex = new Hex();

        for (int i = 0; i < ITERATIONS; i++) {
            final byte[] originalData = new byte[ThreadLocalRandom.current().nextInt(10000) + 1];
            ThreadLocalRandom.current().nextBytes(originalData);

            // Static API: encode bytes to hex char[] then decode back to bytes
            final char[] hexChars = Hex.encodeHex(originalData);
            byte[] decodedBytes = Hex.decodeHex(hexChars);
            assertArrayEquals(originalData, decodedBytes);

            // Instance API with byte[] parameter: encode to hex bytes then decode back
            final byte[] encodedBytes = hex.encode(originalData);
            decodedBytes = hex.decode(encodedBytes);
            assertArrayEquals(originalData, decodedBytes);

            // Instance API with char[] (Object) parameter: encode a hex String, decode the resulting char[]
            String hexString = new String(hexChars);
            char[] reEncodedChars = (char[]) hex.encode(hexString);
            decodedBytes = (byte[]) hex.decode(reEncodedChars);
            assertArrayEquals(StringUtils.getBytesUtf8(hexString), decodedBytes);

            // Instance API with String (Object) parameter: encode a hex String, decode the resulting String
            hexString = new String(hexChars);
            reEncodedChars = (char[]) hex.encode(hexString);
            decodedBytes = (byte[]) hex.decode(new String(reEncodedChars));
            assertArrayEquals(StringUtils.getBytesUtf8(hexString), decodedBytes);
        }
    }
}
