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
public class CodecEncoding_ESTest_test03 extends CodecEncoding_ESTest_scaffolding {

    private static final int CANONICAL_CODEC_INDEX = 13;
    private static final int RUN_LENGTH = 13;
    private static final int[] EXPECTED_RUN_CODEC_SPECIFIER = new int[] { 129, 12, 13 };

    @Test(timeout = 4000)
    public void test03() throws Throwable {
        BHSDCodec defaultCodec = CodecEncoding.getCanonicalCodec(CANONICAL_CODEC_INDEX);
        RunCodec runCodec = new RunCodec(RUN_LENGTH, defaultCodec, defaultCodec);

        int[] specifier = CodecEncoding.getSpecifier(runCodec, defaultCodec);

        assertArrayEquals(EXPECTED_RUN_CODEC_SPECIFIER, specifier);
        assertNotNull(specifier);
    }
}
