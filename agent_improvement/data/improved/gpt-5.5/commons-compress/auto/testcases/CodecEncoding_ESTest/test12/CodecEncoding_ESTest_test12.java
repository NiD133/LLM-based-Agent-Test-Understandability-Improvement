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
public class CodecEncoding_ESTest_test12 extends CodecEncoding_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test12() throws Throwable {
        final BHSDCodec defaultCodec = Codec.CHAR3;
        final PipedInputStream unconnectedHeaderStream = new PipedInputStream();

        try {
            CodecEncoding.getCodec(137, unconnectedHeaderStream, defaultCodec);
            fail("Expecting exception: IOException");
        } catch (IOException e) {
            //
            // Pipe not connected
            //
            verifyException("java.io.PipedInputStream", e);
        }
    }
}
