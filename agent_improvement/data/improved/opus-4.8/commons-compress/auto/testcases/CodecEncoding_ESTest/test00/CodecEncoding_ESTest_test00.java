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
     * Verifies the meta-encoding specifier produced for a RunCodec.
     *
     * The RunCodec has a run length k of 1825 and uses the canonical BYTE1
     * codec for both its A and B sub-codecs. When it is also passed as the
     * "default for band" codec, getSpecifier encodes:
     *   - 122 : the leading byte (run-codec family, derived from k and the
     *           A/B default flags)
     *   - 113 : the kx value describing the run length
     *   - 1, 1 : the one-byte specifier of the BYTE1 codec, repeated for the
     *           A and B sub-codecs
     */
    @Test(timeout = 4000)
    public void test00() throws Throwable {
        BHSDCodec byte1Codec = Codec.BYTE1;
        RunCodec runCodec = new RunCodec(1825, byte1Codec, byte1Codec);

        int[] specifier = CodecEncoding.getSpecifier(runCodec, runCodec);

        assertArrayEquals(new int[] { 122, 113, 1, 1 }, specifier);
    }
}
