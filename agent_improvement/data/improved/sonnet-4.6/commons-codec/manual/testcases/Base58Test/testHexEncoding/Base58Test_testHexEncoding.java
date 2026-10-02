package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class Base58Test_testHexEncoding {

    private static final String HEX_INPUT = "48656c6c6f20576f726c6421";
    private static final String EXPECTED_BASE58 = "5m7UdtXCfQxGvX2K9dLrkNs7AFMS98qn8";

    /**
     * Verifies that a hex string can be Base58-encoded and then decoded back
     * to the original hex string without data loss.
     */
    @Test
    void testHexEncoding() {
        final Base58 codec = new Base58();

        final byte[] encoded = codec.encode(StringUtils.getBytesUtf8(HEX_INPUT));
        final String encodedString = StringUtils.newStringUtf8(encoded);

        assertEquals(EXPECTED_BASE58, encodedString, "Hex encoding failed");

        final byte[] decoded = codec.decode(encodedString);
        assertEquals(HEX_INPUT, StringUtils.newStringUtf8(decoded), "Hex decoding failed");
    }
}
