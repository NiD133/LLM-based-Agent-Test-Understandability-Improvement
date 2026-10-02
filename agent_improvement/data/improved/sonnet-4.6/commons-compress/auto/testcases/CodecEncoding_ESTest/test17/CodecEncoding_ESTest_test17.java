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
public class CodecEncoding_ESTest_test17 extends CodecEncoding_ESTest_scaffolding {

    /**
     * Verifies that once a BufferedInputStream has consumed the underlying byte array,
     * a subsequent getCodec call on the exhausted ByteArrayInputStream throws EOFException.
     *
     * The value 166 falls in the population-codec range (141–188), which triggers reads
     * that the BufferedInputStream eagerly buffers, draining the 2-byte backing array.
     * The value 116 requires reading additional header bytes; with the stream empty, an
     * EOFException is expected.
     */
    @Test(timeout = 4000)
    public void test17() throws Throwable {
        BHSDCodec defaultCodec = Codec.DELTA5;

        // Create a 2-byte backing array and wrap it in a ByteArrayInputStream
        byte[] twoZeroBytes = new byte[2];
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(twoZeroBytes);

        // BufferedInputStream reads ahead, consuming both bytes from byteArrayInputStream
        BufferedInputStream bufferedInputStream = new BufferedInputStream(byteArrayInputStream);

        // Population-codec specifier (166) triggers reads that drain the buffered stream
        CodecEncoding.getCodec(166, bufferedInputStream, defaultCodec);

        // byteArrayInputStream is now exhausted; value 116 requires a header byte, so EOFException is expected
        try {
            CodecEncoding.getCodec(116, byteArrayInputStream, defaultCodec);
            fail("Expecting exception: EOFException");
        } catch (EOFException e) {
            //
            // End of buffer read whilst trying to decode codec
            //
            verifyException("org.apache.commons.compress.harmony.pack200.CodecEncoding", e);
        }
    }
}
