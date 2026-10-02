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
public class CodecEncoding_ESTest_test10 extends CodecEncoding_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test10() throws Throwable {
        PipedInputStream pipedInputStream0 = new PipedInputStream();
        BHSDCodec bHSDCodec0 = Codec.CHAR3;
        try {
            CodecEncoding.getCodec(2756, pipedInputStream0, bHSDCodec0);
            fail("Expecting exception: IOException");
        } catch (IOException e) {
            //
            // Invalid codec encoding byte (2756) found
            //
            verifyException("org.apache.commons.compress.harmony.pack200.CodecEncoding", e);
        }
    }
}
