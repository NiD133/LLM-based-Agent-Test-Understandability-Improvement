package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 * Tests basic round-trip URL encoding and decoding using URLCodec.
 *
 * URLCodec follows the 'www-form-urlencoded' scheme:
 *   - spaces become '+'
 *   - unsafe characters (e.g. '!') become percent-encoded sequences (e.g. '%21')
 *   - safe alphanumeric characters are left as-is
 */
public class URLCodecTest_testBasicEncodeDecode {

    @Test
    void testBasicEncodeDecode() throws Exception {
        final URLCodec urlCodec = new URLCodec();

        final String plainText = "Hello there!";

        // Encode: space → '+', '!' → '%21'; letters are safe and unchanged
        final String encodedText = urlCodec.encode(plainText);
        assertEquals("Hello+there%21", encodedText,
                "Space should be encoded as '+' and '!' as '%21'");

        // Decode must restore the original plain text exactly
        final String decodedText = urlCodec.decode(encodedText);
        assertEquals(plainText, decodedText,
                "Decoding an encoded string must recover the original input");
    }
}
