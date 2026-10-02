package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link URLCodec} uses the charset supplied to its constructor as the
 * default charset for {@link URLCodec#encode(String)}.
 */
public class URLCodecTest_testDefaultEncoding {

    @Test
    void testDefaultEncoding() throws Exception {
        final String plain = "Hello there!";
        final String charsetName = "UnicodeBig";

        // The codec is constructed with "UnicodeBig" as its default charset.
        final URLCodec urlCodec = new URLCodec(charsetName);

        // Encoding once up front to work around a weird quirk in Java 1.2.2.
        urlCodec.encode(plain);

        // Encoding with an explicit charset must match encoding with the default charset,
        // since the default charset is exactly the one passed to the constructor.
        final String encodedWithExplicitCharset = urlCodec.encode(plain, charsetName);
        final String encodedWithDefaultCharset = urlCodec.encode(plain);

        assertEquals(encodedWithExplicitCharset, encodedWithDefaultCharset);
    }
}
