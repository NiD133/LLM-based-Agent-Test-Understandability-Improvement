package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class BCodecTest_testEncodeStringWithNull {

    @Test
    void testEncodeStringWithNull() throws Exception {
        final BCodec bcodec = new BCodec();
        final String sourceText = null;

        final String encodedText = bcodec.encode(sourceText, "charset");

        assertNull(encodedText, "Result should be null");
    }
}
