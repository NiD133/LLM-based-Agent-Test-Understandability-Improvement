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

    // Valid codec encoding values are 0-188; anything outside that range must raise an IOException.
    private static final int INVALID_CODEC_VALUE = 2756;

    @Test(timeout = 4000)
    public void test10() throws Throwable {
        PipedInputStream bandHeadersStream = new PipedInputStream();
        BHSDCodec defaultCodec = Codec.CHAR3;

        try {
            CodecEncoding.getCodec(INVALID_CODEC_VALUE, bandHeadersStream, defaultCodec);
            fail("Expecting exception: IOException");
        } catch (IOException e) {
            verifyException("org.apache.commons.compress.harmony.pack200.CodecEncoding", e);
        }
    }
}
