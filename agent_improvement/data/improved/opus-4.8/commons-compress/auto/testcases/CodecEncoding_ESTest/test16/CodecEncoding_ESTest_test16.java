package org.apache.commons.compress.harmony.pack200;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.io.ByteArrayInputStream;
import java.io.EOFException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CodecEncoding_ESTest_test16 extends CodecEncoding_ESTest_scaffolding {

    /**
     * Encoding value 116 tells {@link CodecEncoding#getCodec} to build an arbitrary
     * BHSDCodec by reading two header bytes from the input stream. Here the stream
     * only holds a single byte, so the second read hits the end of the buffer and
     * an EOFException is thrown.
     */
    @Test(timeout = 4000)
    public void getCodecValue116WithTruncatedHeaderThrowsEOFException() throws Throwable {
        BHSDCodec defaultCodec = Codec.DELTA5;
        // Only one header byte is available; value 116 needs two.
        byte[] truncatedHeader = new byte[1];
        ByteArrayInputStream headerStream = new ByteArrayInputStream(truncatedHeader);

        try {
            CodecEncoding.getCodec(116, headerStream, defaultCodec);
            fail("Expecting exception: EOFException");
        } catch (EOFException e) {
            // "End of buffer read whilst trying to decode codec"
            verifyException("org.apache.commons.compress.harmony.pack200.CodecEncoding", e);
        }
    }
}
