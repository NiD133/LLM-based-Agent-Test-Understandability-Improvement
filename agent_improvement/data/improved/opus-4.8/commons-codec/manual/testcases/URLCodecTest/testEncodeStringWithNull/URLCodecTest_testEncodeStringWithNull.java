package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link URLCodec#encode(String, String)} returns {@code null}
 * when given a {@code null} input string, regardless of the charset name.
 */
public class URLCodecTest_testEncodeStringWithNull {

    @Test
    void testEncodeStringWithNull() throws Exception {
        final URLCodec urlCodec = new URLCodec();
        final String nullInput = null;
        final String charsetName = "charset";

        final String result = urlCodec.encode(nullInput, charsetName);

        assertNull(result, "Encoding a null string should return null");
    }
}
