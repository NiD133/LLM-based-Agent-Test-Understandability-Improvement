package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class PercentCodecTest_testInvalidByte {

    @Test
    void testInvalidByte() throws Exception {
        final byte[] invalidAlwaysEncodeChars = { (byte) -1, (byte) 'A' };

        assertThrows(IllegalArgumentException.class, () -> new PercentCodec(invalidAlwaysEncodeChars, true));
    }
}
