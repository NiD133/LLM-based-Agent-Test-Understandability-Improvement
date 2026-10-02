package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

public class PercentCodecTest_testConfigurablePercentEncoder {

    /**
     * Tests that PercentCodec encodes only the explicitly configured "always-encode" characters
     * and non-ASCII characters, leaving all other ASCII characters unmodified.
     *
     * The codec is configured to always encode 'a', 'b', 'c', 'd', 'e', 'f'.
     * Input "abc123_-.*αβ" should encode as:
     *   %61%62%63  — 'a','b','c' are in the always-encode set (hex: 0x61, 0x62, 0x63)
     *   123_-.*    — these ASCII chars are NOT in the always-encode set, so they pass through unchanged
     *   %CE%B1%CE%B2 — 'α','β' are non-ASCII and are always percent-encoded (UTF-8 bytes)
     */
    @Test
    void testConfigurablePercentEncoder() throws Exception {
        final String input = "abc123_-.*αβ";
        final byte[] alwaysEncodeChars = "abcdef".getBytes(StandardCharsets.UTF_8);
        final PercentCodec percentCodec = new PercentCodec(alwaysEncodeChars, false);

        final byte[] encoded = percentCodec.encode(input.getBytes(StandardCharsets.UTF_8));
        final String encodedString = new String(encoded, StandardCharsets.UTF_8);
        assertEquals("%61%62%63123_-.*%CE%B1%CE%B2", encodedString, "Configurable PercentCodec encoding test");

        final byte[] decoded = percentCodec.decode(encoded);
        final String decodedString = new String(decoded, StandardCharsets.UTF_8);
        assertEquals(input, decodedString, "Configurable PercentCodec decoding test");
    }
}
