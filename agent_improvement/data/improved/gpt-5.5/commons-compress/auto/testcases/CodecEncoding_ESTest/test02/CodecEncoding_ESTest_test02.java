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

    private static final int MAXIMUM_RUN_LENGTH = Integer.MAX_VALUE;
    private static final int[] RUN_CODEC_SPECIFIER_FOR_DEFAULT_BYTE1 = { 124, 524286, 1, 1 };

    @Test(timeout = 4000)
    public void test02() throws Throwable {
        BHSDCodec byte1Codec = Codec.BYTE1;
        RunCodec runCodec = new RunCodec(MAXIMUM_RUN_LENGTH, byte1Codec, byte1Codec);

        int[] specifier = CodecEncoding.getSpecifier(runCodec, runCodec);

        assertArrayEquals(RUN_CODEC_SPECIFIER_FOR_DEFAULT_BYTE1, specifier);
    }
}
