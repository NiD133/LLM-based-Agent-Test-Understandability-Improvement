package org.apache.commons.compress.harmony.pack200;

import org.junit.Test;
import static org.junit.Assert.*;
import java.io.ByteArrayInputStream;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CodecEncoding_ESTest_test04 extends CodecEncoding_ESTest_scaffolding {

    /**
     * Encoding value 116 tells {@link CodecEncoding#getCodec} to build an arbitrary
     * BHSD codec by reading two header bytes from the input stream. Here the two bytes
     * are 93 and 0, which decode into a specific BHSDCodec.
     *
     * The test then round-trips that codec back through {@link CodecEncoding#getSpecifier}
     * and verifies it produces the canonical "116, BHSD-flags, H-1" specifier triple,
     * and that exactly the two header bytes were consumed from the stream.
     */
    @Test(timeout = 4000)
    public void getCodecForValue116ReadsTwoHeaderBytesAndRoundTrips() throws Throwable {
        final BHSDCodec defaultCodec = Codec.CHAR3;

        // A 9-byte buffer whose first two bytes (93, 0) are the BHSD header;
        // the remaining 7 bytes are left untouched.
        final byte[] bandHeaders = new byte[9];
        bandHeaders[0] = (byte) 93;
        final ByteArrayInputStream headerStream = new ByteArrayInputStream(bandHeaders);

        final int encodingValue = 116;
        final Codec decodedCodec = CodecEncoding.getCodec(encodingValue, headerStream, defaultCodec);

        final int[] specifier = CodecEncoding.getSpecifier(decodedCodec, decodedCodec);

        // getCodec read 2 header bytes, leaving 9 - 2 = 7 bytes available.
        assertEquals(7, headerStream.available());
        assertArrayEquals(new int[] { 116, 29, 0 }, specifier);
    }
}
