package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class Base58Test_testHexEncoding {

    private static final String HEX_INPUT = "48656c6c6f20576f726c6421";
    private static final String EXPECTED_BASE58 = "5m7UdtXCfQxGvX2K9dLrkNs7AFMS98qn8";

    @Test
    void testHexEncoding() {
        final Base58 base58 = new Base58();

        final byte[] encoded = base58.encode(StringUtils.getBytesUtf8(HEX_INPUT));
        final byte[] decoded = base58.decode(StringUtils.newStringUtf8(encoded));

        assertEquals(EXPECTED_BASE58, StringUtils.newStringUtf8(encoded), "Hex encoding failed");
        assertEquals(HEX_INPUT, StringUtils.newStringUtf8(decoded), "Hex decoding failed");
    }
}
