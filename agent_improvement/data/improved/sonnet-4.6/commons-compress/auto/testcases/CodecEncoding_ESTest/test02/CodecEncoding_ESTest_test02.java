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
public class CodecEncoding_ESTest_test02 extends CodecEncoding_ESTest_scaffolding {

    /**
     * Verifies that getSpecifier returns the correct encoding descriptor for a RunCodec
     * with an extremely large k value (Integer.MAX_VALUE) and BYTE1 as both sub-codecs.
     *
     * A RunCodec specifier is encoded as:
     *   [first-byte, kx-value, aCodec-specifier..., bCodec-specifier...]
     *
     * With k = Integer.MAX_VALUE (> 65536), the scale factor kb = 3 and
     * kx = k / 4096 - 1 = 524286. Neither sub-codec matches the default (the
     * RunCodec itself), so both are emitted explicitly. BYTE1 has canonical
     * specifier [1], giving the four-element result {124, 524286, 1, 1}.
     */
    @Test(timeout = 4000)
    public void test02() throws Throwable {
        // BYTE1 is the standard single-byte codec used as both sub-codecs of the RunCodec
        BHSDCodec byte1Codec = Codec.BYTE1;

        // Create a RunCodec with the maximum possible k (run-length threshold),
        // using BYTE1 for both the "A" (run) and "B" (remainder) portions
        RunCodec runCodecWithMaxK = new RunCodec(Integer.MAX_VALUE, byte1Codec, byte1Codec);

        // Pass the RunCodec as its own default so neither sub-codec is suppressed
        // (since BYTE1 != the RunCodec, both sub-codec specifiers are included)
        int[] specifier = CodecEncoding.getSpecifier(runCodecWithMaxK, runCodecWithMaxK);

        // Expected layout: [first=124, kx=524286, aSpec=1, bSpec=1]
        //   first = 117 + kb(3) + 4 (kx != 3) + 8*abDef(0) = 124
        //   kx    = Integer.MAX_VALUE / 4096 - 1 = 524286
        //   aSpec = canonical index of BYTE1 = 1
        //   bSpec = canonical index of BYTE1 = 1
        assertArrayEquals(new int[] { 124, 524286, 1, 1 }, specifier);
    }
}
