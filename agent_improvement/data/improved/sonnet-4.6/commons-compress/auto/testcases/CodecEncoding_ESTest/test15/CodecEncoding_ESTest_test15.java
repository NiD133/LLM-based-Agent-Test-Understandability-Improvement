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
public class CodecEncoding_ESTest_test15 extends CodecEncoding_ESTest_scaffolding {

    /**
     * Verifies that decoding a RunCodec from encoding byte 128 (offset 11 within the run-codec range 117-140)
     * produces the correct specifier array {123, 63, 13, 13}.
     *
     * Encoding byte 128 (offset = 128 - 117 = 11) decodes as:
     *   kx=3, kbflag=false → kb=3, k = (3+1)*16^3 = 16384
     *   adef=true  → aCodec = defaultCodec (canonical codec #13 = BHSDCodec(4,256))
     *   bdef=false → bCodec read from stream: byte value 0 → getCodec(0,...) = defaultCodec
     *
     * The resulting RunCodec(16384, canonicalCodec[13], canonicalCodec[13]) produces specifier:
     *   [0] = 123  (first byte: 117 + kb=2 + 4 because kx≠3, abDef=0)
     *   [1] = 63   (kx = 16384/256 - 1 = 63)
     *   [2] = 13   (specifier of aCodec = canonicalCodec[13])
     *   [3] = 13   (specifier of bCodec = canonicalCodec[13])
     */
    @Test(timeout = 4000)
    public void test15() throws Throwable {
        // Canonical codec at index 13 is BHSDCodec(4, 256); used as the default codec
        BHSDCodec defaultCodec = CodecEncoding.getCanonicalCodec(13);

        // The stream provides the extra byte needed to decode the bCodec in the RunCodec.
        // Value 0 in the stream means getCodec(0, ...) returns the defaultCodec.
        byte[] bandHeaderBytes = new byte[1]; // single zero byte
        ByteArrayInputStream bandHeaderStream = new ByteArrayInputStream(bandHeaderBytes);

        // Encoding byte 128 signals a RunCodec; the stream byte is consumed to determine bCodec.
        Codec runCodec = CodecEncoding.getCodec(128, bandHeaderStream, defaultCodec);

        // Confirm the stream byte was fully consumed during codec construction
        assertEquals(0, bandHeaderStream.available());

        // Retrieve the specifier using UNSIGNED5 as the band's default codec reference
        int[] specifier = CodecEncoding.getSpecifier(runCodec, runCodec.UNSIGNED5);

        // Expected: {123, 63, 13, 13} encodes RunCodec(16384, canonicalCodec[13], canonicalCodec[13])
        assertArrayEquals(new int[] { 123, 63, 13, 13 }, specifier);
    }
}
