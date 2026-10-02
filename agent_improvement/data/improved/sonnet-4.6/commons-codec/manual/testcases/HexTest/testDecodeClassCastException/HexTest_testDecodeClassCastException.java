package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

public class HexTest_testDecodeClassCastException {

    /**
     * Verifies that passing an unsupported type (int[]) to Hex.decode(Object) throws
     * DecoderException. The decode method accepts String, byte[], ByteBuffer, and char[];
     * any other type causes an internal ClassCastException that is wrapped as DecoderException.
     */
    @Test
    void testDecodeClassCastException() {
        assertThrows(DecoderException.class, () -> new Hex().decode(new int[] { 65 }));
    }
}
