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

    /**
     * A negative encoding value is invalid: {@link CodecEncoding#getCodec} must reject it
     * with an {@link IllegalArgumentException} ("Encoding cannot be less than zero").
     */
    @Test(timeout = 4000)
    public void getCodecRejectsNegativeEncodingValue() throws Throwable {
        int negativeEncodingValue = -346;
        PipedInputStream headerStream = new PipedInputStream();
        BHSDCodec defaultCodec = Codec.CHAR3;

        try {
            CodecEncoding.getCodec(negativeEncodingValue, headerStream, defaultCodec);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // "Encoding cannot be less than zero"
            verifyException("org.apache.commons.compress.harmony.pack200.CodecEncoding", e);
        }
    }
}
