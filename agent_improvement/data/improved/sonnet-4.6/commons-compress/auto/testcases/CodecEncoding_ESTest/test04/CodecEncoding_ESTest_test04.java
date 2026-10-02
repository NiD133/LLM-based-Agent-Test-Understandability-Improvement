package org.apache.commons.compress.harmony.pack200;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CodecEncoding_ESTest_test04 extends CodecEncoding_ESTest_scaffolding {

    /**
     * Verifies that a non-canonical BHSDCodec decoded from an explicit encoding byte (value 116)
     * round-trips correctly through getCodec() and getSpecifier().
     *
     * Encoding byte 116 tells getCodec() to read two bytes from the stream to construct a BHSDCodec:
     *   - First byte (93 = 0x5D): encodes d=1, s=2, b=4
     *   - Second byte (0):        encodes h=1
     * This yields BHSDCodec(4, 1, 2, 1), which is not in the canonical table.
     *
     * getSpecifier() on a non-canonical BHSDCodec emits a 3-element array:
     *   [116, (d + 2*s + 8*(b-1)), (h-1)] = [116, 29, 0]
     */
    @Test(timeout = 4000)
    public void test04() throws Throwable {
        BHSDCodec defaultCodec = Codec.CHAR3;

        // Band-headers stream: first byte encodes BHSDCodec parameters, second byte encodes H.
        // Remaining 7 bytes are unused padding (all zeros).
        byte[] bandHeaders = new byte[9];
        bandHeaders[0] = (byte) 93; // d=1, s=2, b=4 (binary: 0101_1101)
        ByteArrayInputStream bandHeaderStream = new ByteArrayInputStream(bandHeaders);

        // Value 116 signals an explicit BHSDCodec; getCodec reads 2 bytes from bandHeaderStream.
        Codec decodedCodec = CodecEncoding.getCodec(116, bandHeaderStream, defaultCodec);

        // Confirm that exactly 2 bytes were consumed from the 9-byte stream.
        assertEquals(7, bandHeaderStream.available());

        // The decoded non-canonical codec round-trips to the 3-element specifier [116, 29, 0].
        int[] specifier = CodecEncoding.getSpecifier(decodedCodec, decodedCodec);
        assertArrayEquals(new int[] { 116, 29, 0 }, specifier);
    }
}
