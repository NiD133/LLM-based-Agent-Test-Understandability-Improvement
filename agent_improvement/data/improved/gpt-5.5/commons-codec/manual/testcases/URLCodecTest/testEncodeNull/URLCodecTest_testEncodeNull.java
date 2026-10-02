package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class URLCodecTest_testEncodeNull {

    private void validateState(final URLCodec urlCodec) {
        // No state assertions are required for this scenario.
    }

    @Test
    void testEncodeNull() throws Exception {
        final URLCodec urlCodec = new URLCodec();
        final byte[] plain = null;

        final byte[] encoded = urlCodec.encode(plain);

        assertNull(encoded, "Encoding a null string should return null");
        validateState(urlCodec);
    }
}
