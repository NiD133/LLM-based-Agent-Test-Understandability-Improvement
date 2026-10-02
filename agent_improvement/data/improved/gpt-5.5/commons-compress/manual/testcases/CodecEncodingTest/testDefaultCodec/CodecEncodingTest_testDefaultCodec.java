package org.apache.commons.compress.harmony.pack200;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;

public class CodecEncodingTest_testDefaultCodec {

    @Test
    void testDefaultCodec() throws Pack200Exception, IOException {
        final Codec defaultCodec = new BHSDCodec(2, 16, 0, 0);

        assertEquals(defaultCodec, CodecEncoding.getCodec(0, null, defaultCodec));
    }
}
