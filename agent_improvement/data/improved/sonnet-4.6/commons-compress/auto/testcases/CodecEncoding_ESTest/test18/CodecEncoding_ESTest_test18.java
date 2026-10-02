package org.apache.commons.compress.harmony.pack200;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.io.PipedInputStream;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CodecEncoding_ESTest_test18 extends CodecEncoding_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test18_getCodec_throwsIllegalArgumentException_whenEncodingValueIsNegative() throws Throwable {
        final int negativeEncodingValue = -346;
        BHSDCodec defaultCodec = Codec.CHAR3;
        PipedInputStream inputStream = new PipedInputStream();

        try {
            CodecEncoding.getCodec(negativeEncodingValue, inputStream, defaultCodec);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            verifyException("org.apache.commons.compress.harmony.pack200.CodecEncoding", e);
        }
    }
}
