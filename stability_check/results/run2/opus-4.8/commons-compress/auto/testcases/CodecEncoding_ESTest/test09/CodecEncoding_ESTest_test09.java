package org.apache.commons.compress.harmony.pack200;

import org.junit.Test;
import static org.junit.Assert.*;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CodecEncoding_ESTest_test09 extends CodecEncoding_ESTest_scaffolding {

    /**
     * Encoding value 141 selects a population codec whose favoured, token and
     * unfavoured sub-codecs are read from the supplied stream. All header bytes
     * here are zero, so each sub-codec falls back to the default codec.
     *
     * The stream is wrapped in a BufferedInputStream, which reads the whole
     * 9-byte buffer ahead on the first read; the underlying ByteArrayInputStream
     * therefore reports no remaining bytes afterwards.
     */
    @Test(timeout = 4000)
    public void test09() throws Throwable {
        BHSDCodec defaultCodec = Codec.CHAR3;
        byte[] headerBytes = new byte[9];
        ByteArrayInputStream backingStream = new ByteArrayInputStream(headerBytes);
        BufferedInputStream headerStream = new BufferedInputStream(backingStream);

        CodecEncoding.getCodec(141, headerStream, defaultCodec);

        assertEquals(0, backingStream.available());
    }
}
