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

    /**
     * Verifies that getCodec throws IOException when a Run codec value (137, in
     * the range 117-140) is supplied with a disconnected PipedInputStream.
     *
     * Value 137 triggers the Run codec branch, which immediately tries to read
     * the "kb" byte from the stream. An unconnected PipedInputStream cannot
     * be read from, so it raises "Pipe not connected".
     */
    @Test(timeout = 4000)
    public void test12_getCodec_runCodecValue_disconnectedStream_throwsIOException() throws Throwable {
        // Use CHAR3 as the fallback/default codec
        BHSDCodec defaultCodec = Codec.CHAR3;

        // A PipedInputStream that has never been connected to a PipedOutputStream
        // will throw IOException("Pipe not connected") on the first read attempt.
        PipedInputStream disconnectedStream = new PipedInputStream();

        // Value 137 falls in the Run codec range (117–140); decoding it requires
        // reading additional bytes from the stream, which fails immediately here.
        try {
            CodecEncoding.getCodec(137, disconnectedStream, defaultCodec);
            fail("Expecting exception: IOException");
        } catch (IOException e) {
            // Expected: "Pipe not connected"
            verifyException("java.io.PipedInputStream", e);
        }
    }
}
