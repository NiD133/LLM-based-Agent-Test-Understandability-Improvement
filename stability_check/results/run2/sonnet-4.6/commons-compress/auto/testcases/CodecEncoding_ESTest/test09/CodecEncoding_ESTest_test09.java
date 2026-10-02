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
     * Value 141 is the first specifier in the PopulationCodec range (141–188).
     * With all-zero bytes in the stream, both fCodec and uCodec resolve to the
     * default codec, so the call succeeds and returns a PopulationCodec.
     *
     * The key side-effect under test: wrapping the ByteArrayInputStream in a
     * BufferedInputStream causes the buffer to eagerly read all 9 bytes from
     * the underlying stream on its first read, draining it completely.
     * After the call, available() on the ByteArrayInputStream must be 0.
     */
    @Test(timeout = 4000)
    public void test09() throws Throwable {
        // Codec.CHAR3 is used as the default codec passed to getCodec
        BHSDCodec defaultCodec = Codec.CHAR3;

        // Nine zero-bytes: the stream supplies codec-specifier bytes read during
        // PopulationCodec construction (fCodec, tCodec, uCodec each read one byte)
        byte[] codecSpecifierBytes = new byte[9];
        ByteArrayInputStream underlyingStream = new ByteArrayInputStream(codecSpecifierBytes);

        // BufferedInputStream reads ahead eagerly, draining underlyingStream into
        // its internal buffer on the very first read
        BufferedInputStream bufferedStream = new BufferedInputStream(underlyingStream);

        // 141 is the first PopulationCodec specifier; with zero bytes fdef=false,
        // udef=false, tdef=false, so it reads fCodec, tCodec, uCodec from the stream
        CodecEncoding.getCodec(141, bufferedStream, defaultCodec);

        // The BufferedInputStream read-ahead has emptied the underlying source
        assertEquals(0, underlyingStream.available());
    }
}
