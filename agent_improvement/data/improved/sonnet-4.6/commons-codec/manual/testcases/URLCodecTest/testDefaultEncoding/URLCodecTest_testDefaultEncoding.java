package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class URLCodecTest_testDefaultEncoding {

    @Test
    void testDefaultEncoding() throws Exception {
        final String plain = "Hello there!";
        final URLCodec urlCodec = new URLCodec("UnicodeBig");

        // Warm-up call needed to work around a quirk in Java 1.2.2
        urlCodec.encode(plain);

        final String encodedWithExplicitCharset = urlCodec.encode(plain, "UnicodeBig");
        final String encodedWithDefaultCharset = urlCodec.encode(plain);

        // Encoding with an explicit charset that matches the codec's default should yield the same result
        assertEquals(encodedWithExplicitCharset, encodedWithDefaultCharset);
    }
}
