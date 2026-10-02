package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

public class PercentCodecTest_testInvalidByte {

    /**
     * PercentCodec only accepts US-ASCII characters (byte values >= 0) in the
     * "always-encode" list. A negative byte value such as -1 is outside the
     * valid US-ASCII range, so the constructor must reject it with an
     * IllegalArgumentException.
     */
    @Test
    void testInvalidByte() throws Exception {
        // (byte) -1 is a negative (non-US-ASCII) value; 'A' is a valid filler to
        // confirm the entire array is validated, not just the first element.
        final byte[] alwaysEncodeCharsWithNegativeByte = { (byte) -1, (byte) 'A' };

        assertThrows(IllegalArgumentException.class,
                () -> new PercentCodec(alwaysEncodeCharsWithNegativeByte, true));
    }
}
