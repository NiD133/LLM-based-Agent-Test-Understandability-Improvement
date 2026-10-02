package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.codec.EncoderException;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link PercentCodec#encode(Object)} rejects objects it cannot
 * Percent-encode. The codec only accepts {@code byte[]} payloads, so any other
 * object type must be reported as an error.
 */
public class PercentCodecTest_testEncodeUnsupportedObject {

    @Test
    void encodeShouldRejectNonByteArrayObject() {
        final PercentCodec percentCodec = new PercentCodec();

        // A String is not a byte[], so encoding it is unsupported and must fail.
        final Object unsupportedInput = "test";

        assertThrows(EncoderException.class, () -> percentCodec.encode(unsupportedInput));
    }
}
