package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

public class BCodecTest_testEncodeStringWithNull {

    @Test
    void testEncodeStringWithNull() throws Exception {
        final BCodec bcodec = new BCodec();
        final String nullInput = null;
        final String result = bcodec.encode(nullInput, "charset");
        assertNull(result, "Result should be null");
    }
}
