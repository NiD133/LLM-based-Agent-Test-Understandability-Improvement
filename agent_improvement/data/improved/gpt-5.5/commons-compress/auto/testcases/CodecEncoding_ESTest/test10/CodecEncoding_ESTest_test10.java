package org.apache.commons.compress.harmony.pack200;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.io.IOException;
import java.io.PipedInputStream;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CodecEncoding_ESTest_test10 extends CodecEncoding_ESTest_scaffolding {

    private static final int INVALID_CODEC_ENCODING = 2756;
    private static final BHSDCodec DEFAULT_CODEC = Codec.CHAR3;
    private static final String EXPECTED_EXCEPTION_MESSAGE = "Expecting exception: IOException";

    @Test(timeout = 4000)
    public void test10() throws Throwable {
        PipedInputStream bandHeaders = new PipedInputStream();

        try {
            CodecEncoding.getCodec(INVALID_CODEC_ENCODING, bandHeaders, DEFAULT_CODEC);
            fail(EXPECTED_EXCEPTION_MESSAGE);
        } catch (IOException e) {
            //
            // Invalid codec encoding byte (2756) found
            //
            verifyException("org.apache.commons.compress.harmony.pack200.CodecEncoding", e);
        }
    }
}
