package org.apache.commons.compress.harmony.pack200;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.EOFException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CodecEncoding_ESTest_test17 extends CodecEncoding_ESTest_scaffolding {

    /**
     * Decoding the codec for value 116 reads two header bytes from the input
     * stream. When the stream has already been exhausted, the first read
     * returns -1 and {@code getCodec} reports it as an {@link EOFException}.
     *
     * <p>Here the two available bytes are drained by an earlier decode that
     * goes through a {@link BufferedInputStream} wrapper (which buffers and
     * consumes them from the shared underlying array stream), so the later
     * read of value 116 directly from the now-empty underlying stream hits
     * end-of-buffer.</p>
     */
    @Test(timeout = 4000)
    public void decodingValue116OnExhaustedStreamThrowsEOFException() throws Throwable {
        BHSDCodec defaultCodec = Codec.DELTA5;

        // Backing stream holds only two bytes of header data.
        byte[] headerBytes = new byte[2];
        ByteArrayInputStream underlyingStream = new ByteArrayInputStream(headerBytes);

        // The buffered wrapper reads ahead and drains the two bytes from the
        // underlying stream while decoding value 166.
        BufferedInputStream bufferedStream = new BufferedInputStream(underlyingStream);
        CodecEncoding.getCodec(166, bufferedStream, defaultCodec);

        // The underlying stream is now empty, so decoding value 116 (which
        // needs to read a header byte) fails at end-of-buffer.
        try {
            CodecEncoding.getCodec(116, underlyingStream, defaultCodec);
            fail("Expecting exception: EOFException");
        } catch (EOFException e) {
            // End of buffer read whilst trying to decode codec
            verifyException("org.apache.commons.compress.harmony.pack200.CodecEncoding", e);
        }
    }
}
