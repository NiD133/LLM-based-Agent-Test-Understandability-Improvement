package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

public class PercentCodecTest_testDecodeInvalidEncodedResultDecoding {

    @Test
    void testDecodeInvalidEncodedResultDecoding() throws Exception {
        final String nonAsciiInput = "\u03B1\u03B2";
        final PercentCodec percentCodec = new PercentCodec();
        final byte[] encodedInput = percentCodec.encode(nonAsciiInput.getBytes(StandardCharsets.UTF_8));
        final byte[] encodedInputMissingLastByte = Arrays.copyOf(encodedInput, encodedInput.length - 1);

        try {
            percentCodec.decode(encodedInputMissingLastByte);
        } catch (final Exception exception) {
            assertTrue(DecoderException.class.isInstance(exception)
                    && ArrayIndexOutOfBoundsException.class.isInstance(exception.getCause()));
        }
    }
}
