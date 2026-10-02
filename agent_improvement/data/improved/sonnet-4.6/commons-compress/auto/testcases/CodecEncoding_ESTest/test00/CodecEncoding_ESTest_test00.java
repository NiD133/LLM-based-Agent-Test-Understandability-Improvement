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
public class CodecEncoding_ESTest_test00 extends CodecEncoding_ESTest_scaffolding {

    /**
     * Verifies that getSpecifier correctly encodes a RunCodec whose k=1825 and
     * both sub-codecs are BYTE1.
     *
     * k=1825 falls in the [257, 4096] range, so kb=1 and kx = k/16 - 1 = 113.
     * Because neither sub-codec equals the supplied default (runCodec itself),
     * abDef=0, giving first-byte = 117 + kb(1) + 4 + 0 = 122.
     * BYTE1 is canonical codec #1, so each sub-codec specifier is [1].
     * Expected specifier: [122 (run-codec marker), 113 (kx), 1 (A=BYTE1), 1 (B=BYTE1)].
     */
    @Test(timeout = 4000)
    public void test00() throws Throwable {
        // BYTE1 is canonical codec index 1: BHSDCodec(1, 256)
        BHSDCodec byteCodec = Codec.BYTE1;

        // Build a RunCodec: run length k=1825, both phases use BYTE1
        RunCodec runCodec = new RunCodec(1825, byteCodec, byteCodec);

        // Ask for the specifier; use runCodec itself as the "default for band"
        // so that neither A nor B sub-codec matches the default (abDef=0)
        int[] specifier = CodecEncoding.getSpecifier(runCodec, runCodec);

        int[] expectedSpecifier = { 122, 113, 1, 1 };
        assertArrayEquals(expectedSpecifier, specifier);
    }
}
