package org.apache.commons.compress.harmony.pack200;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.IOException;
import org.junit.jupiter.api.Test;

public class CodecEncodingTest_testDefaultCodec {

    @Test
    void testDefaultCodec() throws Pack200Exception, IOException {
        // Per Pack200 spec, encoding value 0 is reserved to mean "use the default codec"
        final int encodingValueForDefault = 0;
        final Codec defaultCodec = new BHSDCodec(2, 16, 0, 0);

        final Codec result = CodecEncoding.getCodec(encodingValueForDefault, null, defaultCodec);

        assertEquals(defaultCodec, result,
            "Encoding value 0 should cause getCodec to return the supplied default codec");
    }
}
