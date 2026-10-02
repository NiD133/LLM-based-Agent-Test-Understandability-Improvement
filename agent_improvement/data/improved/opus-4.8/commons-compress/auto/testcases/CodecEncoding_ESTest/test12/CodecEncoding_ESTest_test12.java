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
public class CodecEncoding_ESTest_test12 extends CodecEncoding_ESTest_scaffolding {

    /**
     * Encoding value 137 selects a "run codec" (values 117..140), whose decoding
     * requires reading additional header bytes from the supplied input stream.
     * When that stream is a {@link PipedInputStream} that has never been connected
     * to a {@link java.io.PipedOutputStream}, the first read fails and getCodec
     * propagates the resulting IOException ("Pipe not connected").
     */
    @Test(timeout = 4000)
    public void getCodec_withUnconnectedPipe_throwsIOException() throws Throwable {
        int runCodecEncodingValue = 137;
        BHSDCodec defaultCodec = Codec.CHAR3;
        PipedInputStream unconnectedPipe = new PipedInputStream();

        try {
            CodecEncoding.getCodec(runCodecEncodingValue, unconnectedPipe, defaultCodec);
            fail("Expecting exception: IOException");
        } catch (IOException e) {
            // Reading from the unconnected pipe fails with "Pipe not connected".
            verifyException("java.io.PipedInputStream", e);
        }
    }
}
