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
public class CodecEncoding_ESTest_test09 extends CodecEncoding_ESTest_scaffolding {

    /**
     * Verifies that getCodec with a PopulationCodec specifier (value=141) consumes
     * bytes from the stream for the favoured, token, and unfavoured sub-codec fields.
     *
     * Value 141 falls in the PopulationCodec range (141-188) with offset=0:
     *   fdef=false, udef=false, tdef=false → three codec bytes are read from the stream.
     * The BufferedInputStream reads all available bytes into its buffer on the first read,
     * so the underlying ByteArrayInputStream is fully drained after the call.
     */
    @Test(timeout = 4000)
    public void test09() throws Throwable {
        // Use CHAR3 as the default codec (passed as fallback for zero-valued sub-codec bytes)
        BHSDCodec defaultCodec = Codec.CHAR3;

        // Nine zero bytes: each zero byte causes getCodec to return the defaultCodec,
        // so the stream is consumed as sub-codec specifiers without further recursion
        byte[] streamBytes = new byte[9];
        ByteArrayInputStream underlyingStream = new ByteArrayInputStream(streamBytes);

        // BufferedInputStream reads all bytes into its buffer on first access,
        // draining underlyingStream completely
        BufferedInputStream bufferedStream = new BufferedInputStream(underlyingStream);

        // Value 141 triggers PopulationCodec parsing: reads fCodec, tCodec, uCodec from stream
        CodecEncoding.getCodec(141, bufferedStream, defaultCodec);

        // After getCodec reads from bufferedStream, the BufferedInputStream has pulled
        // all bytes from underlyingStream into its internal buffer
        assertEquals(0, underlyingStream.available());
    }
}
