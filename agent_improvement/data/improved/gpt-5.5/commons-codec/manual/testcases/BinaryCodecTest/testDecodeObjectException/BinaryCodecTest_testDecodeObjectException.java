package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

public class BinaryCodecTest_testDecodeObjectException {

    @Test
    void testDecodeObjectException() {
        final BinaryCodec codec = new BinaryCodec();

        assertThrows(DecoderException.class, () -> codec.decode(new Object()));
    }
}
