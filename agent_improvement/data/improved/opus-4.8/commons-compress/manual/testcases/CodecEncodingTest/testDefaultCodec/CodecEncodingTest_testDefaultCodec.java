package org.apache.commons.compress.harmony.pack200;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;

public class CodecEncodingTest_testDefaultCodec {

    /**
     * When {@link CodecEncoding#getCodec} is called with the encoding value {@code 0},
     * the spec says the supplied default codec should be returned unchanged.
     */
    @Test
    void testDefaultCodec() throws Pack200Exception, IOException {
        final Codec defaultCodec = new BHSDCodec(2, 16, 0, 0);

        final Codec resolvedCodec = CodecEncoding.getCodec(0, null, defaultCodec);

        assertEquals(defaultCodec, resolvedCodec);
    }
}
