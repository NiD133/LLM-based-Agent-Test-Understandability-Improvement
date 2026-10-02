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
public class CodecEncoding_ESTest_test11 extends CodecEncoding_ESTest_scaffolding {

    private static final int DEFAULT_CODEC_ENCODING = 13;
    private static final int CANONICAL_CODEC_ENCODING = 2;
    private static final long EXPECTED_DEFAULT_CODEC_LARGEST_VALUE = 4294967293L;
    private static final long EXPECTED_CANONICAL_CODEC_SMALLEST_VALUE = -128L;

    @Test(timeout = 4000)
    public void test11() throws Throwable {
        BHSDCodec defaultCodec = CodecEncoding.getCanonicalCodec(DEFAULT_CODEC_ENCODING);
        byte[] emptyBandHeader = new byte[2];
        ByteArrayInputStream bandHeaderInput = new ByteArrayInputStream(emptyBandHeader);

        BHSDCodec canonicalCodec = (BHSDCodec) CodecEncoding.getCodec(CANONICAL_CODEC_ENCODING, bandHeaderInput, defaultCodec);

        assertEquals(EXPECTED_DEFAULT_CODEC_LARGEST_VALUE, defaultCodec.largest());
        assertEquals(EXPECTED_CANONICAL_CODEC_SMALLEST_VALUE, canonicalCodec.smallest());
    }
}
