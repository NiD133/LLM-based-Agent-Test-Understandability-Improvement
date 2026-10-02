package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

public class PercentCodecTest_testConfigurablePercentEncoder {

    private static final String INPUT = "abc123_-.*\u03B1\u03B2";
    private static final String ALWAYS_ENCODE = "abcdef";
    private static final String EXPECTED_ENCODED = "%61%62%63123_-.*%CE%B1%CE%B2";

    @Test
    void testConfigurablePercentEncoder() throws Exception {
        final byte[] alwaysEncodeBytes = ALWAYS_ENCODE.getBytes(StandardCharsets.UTF_8);
        final PercentCodec percentCodec = new PercentCodec(alwaysEncodeBytes, false);

        final byte[] inputBytes = INPUT.getBytes(StandardCharsets.UTF_8);
        final byte[] encoded = percentCodec.encode(inputBytes);
        final String encodedString = new String(encoded, StandardCharsets.UTF_8);

        assertEquals(EXPECTED_ENCODED, encodedString, "Configurable PercentCodec encoding test");

        final byte[] decoded = percentCodec.decode(encoded);
        assertEquals(new String(decoded, StandardCharsets.UTF_8), INPUT, "Configurable PercentCodec decoding test");
    }
}
