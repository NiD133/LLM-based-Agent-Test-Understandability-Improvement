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
public class CodecEncoding_ESTest_test15 extends CodecEncoding_ESTest_scaffolding {

    private static final int DEFAULT_CANONICAL_CODEC_INDEX = 13;
    private static final int RUN_CODEC_ENCODING_BYTE = 128;
    private static final int[] EXPECTED_RUN_CODEC_SPECIFIER = new int[] { 123, 63, 13, 13 };

    @Test(timeout = 4000)
    public void test15() throws Throwable {
        BHSDCodec defaultCodec = CodecEncoding.getCanonicalCodec(DEFAULT_CANONICAL_CODEC_INDEX);
        byte[] bandHeaderBytes = new byte[1];
        ByteArrayInputStream bandHeaderInput = new ByteArrayInputStream(bandHeaderBytes);

        Codec decodedCodec = CodecEncoding.getCodec(RUN_CODEC_ENCODING_BYTE, bandHeaderInput, defaultCodec);
        int[] specifier = CodecEncoding.getSpecifier(decodedCodec, decodedCodec.UNSIGNED5);

        assertEquals(0, bandHeaderInput.available());
        assertArrayEquals(EXPECTED_RUN_CODEC_SPECIFIER, specifier);
    }
}
