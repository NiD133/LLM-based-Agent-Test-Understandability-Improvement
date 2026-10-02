package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

public class PercentCodecTest_testSafeCharEncodeDecodeObject {

    // Characters that are never percent-encoded: unreserved ASCII alphanum plus _-.*
    private static final String SAFE_CHARS = "abc123_-.*";

    @Test
    void testSafeCharEncodeDecodeObject() throws Exception {
        // No extra chars forced to encode; plusForSpace enabled (irrelevant here since input has no spaces)
        final PercentCodec codec = new PercentCodec(null, true);

        final byte[] inputBytes = SAFE_CHARS.getBytes(StandardCharsets.UTF_8);

        // Safe chars should pass through encoding unchanged
        final byte[] encodedBytes = (byte[]) codec.encode((Object) inputBytes);
        final String encodedString = new String(encodedBytes, StandardCharsets.UTF_8);
        assertEquals(SAFE_CHARS, encodedString, "Basic PercentCodec safe char encoding test");

        // Decoding the encoded bytes should reproduce the original input
        final byte[] decodedBytes = (byte[]) codec.decode((Object) encodedBytes);
        final String decodedString = new String(decodedBytes, StandardCharsets.UTF_8);
        assertEquals(SAFE_CHARS, decodedString, "Basic PercentCodec safe char decoding test");
    }
}
