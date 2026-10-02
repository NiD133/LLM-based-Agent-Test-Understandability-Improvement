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
public class CodecEncoding_ESTest_test14 extends CodecEncoding_ESTest_scaffolding {

    /**
     * Encoding value 130 selects a "run codec" (the 117..140 range), which forces
     * {@link CodecEncoding#getCodec} to read additional header bytes from the input
     * stream. The stream here is a {@link PipedInputStream} that was never connected
     * to a source, so the read fails with an IOException ("Pipe not connected").
     */
    @Test(timeout = 4000)
    public void getCodecFromUnconnectedPipeThrowsIOException() throws Throwable {
        BHSDCodec defaultCodec = Codec.CHAR3;
        PipedInputStream unconnectedPipe = new PipedInputStream();

        try {
            CodecEncoding.getCodec(130, unconnectedPipe, defaultCodec);
            fail("Expecting exception: IOException");
        } catch (IOException e) {
            // Pipe not connected
            verifyException("java.io.PipedInputStream", e);
        }
    }
}
