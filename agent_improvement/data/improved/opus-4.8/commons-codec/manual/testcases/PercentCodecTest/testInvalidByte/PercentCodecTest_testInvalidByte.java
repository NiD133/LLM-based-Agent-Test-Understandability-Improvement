package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link PercentCodec} rejects "always encode" bytes that are
 * outside the valid US-ASCII range (i.e. negative bytes).
 */
public class PercentCodecTest_testInvalidByte {

    @Test
    void constructorRejectsNegativeAlwaysEncodeByte() {
        // The first byte (-1) is negative and therefore not a valid US-ASCII
        // character; the second ('A') is valid. The constructor must reject the
        // whole array because of the invalid byte.
        final byte[] alwaysEncodeChars = { (byte) -1, (byte) 'A' };
        final boolean plusForSpace = true;

        assertThrows(IllegalArgumentException.class,
                () -> new PercentCodec(alwaysEncodeChars, plusForSpace));
    }
}
