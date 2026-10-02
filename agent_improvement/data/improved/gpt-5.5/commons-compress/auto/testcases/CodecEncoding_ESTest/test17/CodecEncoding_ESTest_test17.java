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

    private static final int CUSTOM_BHSD_ENCODING = 116;
    private static final int POPULATION_CODEC_ENCODING = 166;

    @Test(timeout = 4000)
    public void test17() throws Throwable {
        BHSDCodec defaultCodec = Codec.DELTA5;
        byte[] codecHeaderBytes = new byte[2];
        ByteArrayInputStream sharedHeaderStream = new ByteArrayInputStream(codecHeaderBytes);
        BufferedInputStream bufferedHeaderStream = new BufferedInputStream(sharedHeaderStream);

        CodecEncoding.getCodec(POPULATION_CODEC_ENCODING, bufferedHeaderStream, defaultCodec);

        try {
            CodecEncoding.getCodec(CUSTOM_BHSD_ENCODING, sharedHeaderStream, defaultCodec);
            fail("Expecting exception: EOFException");
        } catch (EOFException e) {
            //
            // End of buffer read whilst trying to decode codec
            //
            verifyException("org.apache.commons.compress.harmony.pack200.CodecEncoding", e);
        }
    }
}
