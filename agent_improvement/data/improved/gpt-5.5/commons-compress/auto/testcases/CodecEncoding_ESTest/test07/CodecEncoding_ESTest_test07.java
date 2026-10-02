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
public class CodecEncoding_ESTest_test07 extends CodecEncoding_ESTest_scaffolding {

    private static final int POPULATION_CODEC_WITH_EXPLICIT_TOKEN_CODEC = 142;

    @Test(timeout = 4000)
    public void test07() throws Throwable {
        BHSDCodec defaultCodec = Codec.CHAR3;
        InputStream missingBandHeaders = null;

        try {
            CodecEncoding.getCodec(POPULATION_CODEC_WITH_EXPLICIT_TOKEN_CODEC, missingBandHeaders, defaultCodec);
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException e) {
            verifyException("org.apache.commons.compress.harmony.pack200.CodecEncoding", e);
        }
    }
}
