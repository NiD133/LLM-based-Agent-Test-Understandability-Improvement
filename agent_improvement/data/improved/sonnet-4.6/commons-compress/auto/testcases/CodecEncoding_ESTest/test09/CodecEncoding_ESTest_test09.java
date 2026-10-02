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
     * Value 141 is the first byte in the PopulationCodec range (141–188).
     * offset = 141 - 141 = 0, so fdef=false, udef=false, tdefl=0 (tdef=false).
     * The non-tdef branch reads three codec specifiers from the stream (fCodec,
     * tCodec, uCodec); each byte is 0, which maps to the default codec.
     * BufferedInputStream drains all 9 bytes from the underlying
     * ByteArrayInputStream into its internal buffer on the first read, so
     * byteArrayInputStream.available() becomes 0 after the call.
     */
    @Test(timeout = 4000)
    public void test09() throws Throwable {
        // Default codec used when a specifier byte of 0 is read from the stream
        BHSDCodec defaultCodec = Codec.CHAR3;

        // Nine zero bytes: BufferedInputStream will buffer all of them at once,
        // and each 0 specifier byte resolves the sub-codec to defaultCodec
        byte[] specifierBytes = new byte[9];
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(specifierBytes);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(byteArrayInputStream);

        // 141 triggers the PopulationCodec (non-tdef) path; reads fCodec, tCodec,
        // and uCodec specifiers from the stream
        CodecEncoding.getCodec(141, bufferedInputStream, defaultCodec);

        // BufferedInputStream pulled all bytes from the underlying stream into its
        // buffer during the first fill, so the source stream is now exhausted
        assertEquals(0, byteArrayInputStream.available());
    }
}
