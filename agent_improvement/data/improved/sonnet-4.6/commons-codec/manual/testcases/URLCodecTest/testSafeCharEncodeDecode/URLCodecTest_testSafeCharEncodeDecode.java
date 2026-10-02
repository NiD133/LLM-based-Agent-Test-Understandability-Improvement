package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 * Tests that URL-safe characters (alphanumerics plus {@code - _ . *}) pass through
 * {@link URLCodec} encode/decode without any percent-encoding transformation.
 */
public class URLCodecTest_testSafeCharEncodeDecode {

    @Test
    void testSafeCharEncodeDecode() throws Exception {
        final URLCodec urlCodec = new URLCodec();

        // All characters in this string are defined as URL-safe by URLCodec:
        // letters, digits, and the special symbols '-', '_', '.', '*'.
        final String plain = "abc123_-.*";

        final String encoded = urlCodec.encode(plain);
        assertEquals(plain, encoded, "Safe chars URL encoding test");
        assertEquals(plain, urlCodec.decode(encoded), "Safe chars URL decoding test");
    }
}
