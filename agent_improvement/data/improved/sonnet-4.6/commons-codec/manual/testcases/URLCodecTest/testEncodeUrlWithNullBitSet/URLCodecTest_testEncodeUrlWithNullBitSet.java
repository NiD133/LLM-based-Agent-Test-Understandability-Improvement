package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

public class URLCodecTest_testEncodeUrlWithNullBitSet {

    @Test
    void testEncodeUrlWithNullBitSet() throws Exception {
        // Passing null as the safe-char BitSet causes encodeUrl to fall back to
        // the default WWW_FORM_URL_SAFE set: spaces become '+' and '!' becomes '%21'.
        final URLCodec urlCodec = new URLCodec();
        final String plainText = "Hello there!";
        final byte[] plainBytes = plainText.getBytes(StandardCharsets.UTF_8);

        final String encoded = new String(URLCodec.encodeUrl(null, plainBytes));
        assertEquals("Hello+there%21", encoded,
                "null BitSet should use default safe chars: space -> '+', '!' -> '%21'");

        final String decoded = urlCodec.decode(encoded);
        assertEquals(plainText, decoded,
                "Decoding the URL-encoded string should restore the original plain text");
    }
}
