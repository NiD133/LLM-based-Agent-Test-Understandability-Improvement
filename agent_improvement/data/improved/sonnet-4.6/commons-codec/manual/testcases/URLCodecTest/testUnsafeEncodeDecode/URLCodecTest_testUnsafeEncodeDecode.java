package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 * Tests that URLCodec correctly percent-encodes characters that are not safe
 * in a www-form-urlencoded context and can round-trip them back to the original.
 */
public class URLCodecTest_testUnsafeEncodeDecode {

    // Characters outside the safe set (alphanumerics, '-', '_', '.', '*', ' ') must be
    // percent-encoded as %XX by URLCodec.
    private static final String UNSAFE_CHARS = "~!@#$%^&()+{}\"\\;:`,/[]";
    private static final String UNSAFE_CHARS_ENCODED =
            "%7E%21%40%23%24%25%5E%26%28%29%2B%7B%7D%22%5C%3B%3A%60%2C%2F%5B%5D";

    @Test
    void testUnsafeEncodeDecode() throws Exception {
        final URLCodec urlCodec = new URLCodec();

        final String encoded = urlCodec.encode(UNSAFE_CHARS);
        assertEquals(UNSAFE_CHARS_ENCODED, encoded, "Unsafe chars URL encoding test");

        assertEquals(UNSAFE_CHARS, urlCodec.decode(encoded), "Unsafe chars URL decoding test");
    }
}
