package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link URLCodec} performs a lossless round trip (encode then decode)
 * for a string made up entirely of characters that are <em>not</em> URL safe and
 * therefore must be percent-escaped.
 */
public class URLCodecTest_testUnsafeEncodeDecode {

    @Test
    void testUnsafeEncodeDecode() throws Exception {
        final URLCodec urlCodec = new URLCodec();

        // A string composed only of characters that are unsafe in www-form-urlencoded form.
        final String plainText = "~!@#$%^&()+{}\"\\;:`,/[]";

        // Every unsafe character is expected to be replaced by its "%XX" escape sequence.
        final String expectedEncoded =
                "%7E%21%40%23%24%25%5E%26%28%29%2B%7B%7D%22%5C%3B%3A%60%2C%2F%5B%5D";

        final String actualEncoded = urlCodec.encode(plainText);
        assertEquals(expectedEncoded, actualEncoded, "Unsafe chars URL encoding test");

        // Decoding the encoded form must reproduce the original string exactly.
        assertEquals(plainText, urlCodec.decode(actualEncoded), "Unsafe chars URL decoding test");
    }
}
