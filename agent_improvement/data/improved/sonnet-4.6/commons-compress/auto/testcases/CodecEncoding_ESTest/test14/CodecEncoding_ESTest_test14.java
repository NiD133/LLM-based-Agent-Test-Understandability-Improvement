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
public class CodecEncoding_ESTest_test14 extends CodecEncoding_ESTest_scaffolding {

    /**
     * Encoding value 130 falls in the Run codec range (117-140), which requires
     * reading an additional byte from the stream to determine the run length (kb).
     * An unconnected PipedInputStream throws IOException("Pipe not connected") on
     * any read attempt, so getCodec must propagate that IOException to the caller.
     */
    @Test(timeout = 4000)
    public void test14() throws Throwable {
        // Codec.CHAR3 is used as the fallback/default codec
        BHSDCodec defaultCodec = Codec.CHAR3;

        // A PipedInputStream with no connected PipedOutputStream; any read throws IOException
        PipedInputStream unconnectedStream = new PipedInputStream();

        try {
            // Value 130 is a Run codec specifier that must read from the stream to decode
            // the run-length parameter (kb). The unconnected pipe causes that read to fail.
            CodecEncoding.getCodec(130, unconnectedStream, defaultCodec);
            fail("Expecting exception: IOException");
        } catch (IOException e) {
            // PipedInputStream throws "Pipe not connected" when it has no connected source
            verifyException("java.io.PipedInputStream", e);
        }
    }
}
