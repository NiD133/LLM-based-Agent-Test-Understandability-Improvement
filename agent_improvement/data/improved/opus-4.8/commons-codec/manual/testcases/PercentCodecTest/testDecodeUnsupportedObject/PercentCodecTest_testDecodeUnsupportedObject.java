package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link PercentCodec#decode(Object)} rejects objects it cannot handle.
 *
 * <p>The codec only knows how to Percent-decode {@code byte[]} payloads. When asked to
 * decode any other object type, it must signal the unsupported input by throwing a
 * {@link DecoderException} rather than silently failing.</p>
 */
public class PercentCodecTest_testDecodeUnsupportedObject {

    @Test
    void decodeShouldRejectUnsupportedObjectType() {
        final PercentCodec percentCodec = new PercentCodec();

        // A String is not a byte[], so the codec has no way to decode it.
        final Object unsupportedInput = "test";

        assertThrows(DecoderException.class, () -> percentCodec.decode(unsupportedInput));
    }
}
