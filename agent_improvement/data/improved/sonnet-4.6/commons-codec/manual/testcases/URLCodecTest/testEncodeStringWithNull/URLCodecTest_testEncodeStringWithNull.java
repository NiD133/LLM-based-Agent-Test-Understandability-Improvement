package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

public class URLCodecTest_testEncodeStringWithNull {

    @Test
    void testEncodeStringWithNull() throws Exception {
        final URLCodec urlCodec = new URLCodec();
        final String nullInput = null;
        // URLCodec.encode(null, charset) must return null rather than throw
        final String result = urlCodec.encode(nullInput, "charset");
        assertNull(result, "Result should be null");
    }
}
