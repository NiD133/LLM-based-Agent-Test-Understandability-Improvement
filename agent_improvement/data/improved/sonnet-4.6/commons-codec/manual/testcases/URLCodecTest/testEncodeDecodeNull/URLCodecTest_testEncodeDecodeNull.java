package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

/**
 * Tests that URLCodec gracefully handles null input by returning null
 * for both encode and decode operations, rather than throwing an exception.
 */
public class URLCodecTest_testEncodeDecodeNull {

    @Test
    void testEncodeDecodeNull() throws Exception {
        final URLCodec urlCodec = new URLCodec();

        assertNull(urlCodec.encode((String) null), "Null string URL encoding test");
        assertNull(urlCodec.decode((String) null), "Null string URL decoding test");
    }
}
