package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class URLCodecTest_testEncodeDecodeNull {

    private void validateState(final URLCodec urlCodec) {
        // no tests for now.
    }

    @Test
    void testEncodeDecodeNull() throws Exception {
        final URLCodec urlCodec = new URLCodec();

        assertNull(urlCodec.encode((String) null), "Null string URL encoding test");
        assertNull(urlCodec.decode((String) null), "Null string URL decoding test");
        validateState(urlCodec);
    }
}
