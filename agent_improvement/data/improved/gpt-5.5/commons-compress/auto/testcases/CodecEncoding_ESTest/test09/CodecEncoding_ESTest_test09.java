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

    private static final int POPULATION_CODEC_WITH_EXPLICIT_CODECS = 141;
    private static final int BAND_HEADER_LENGTH = 9;

    @Test(timeout = 4000)
    public void test09() throws Throwable {
        BHSDCodec defaultCodec = Codec.CHAR3;
        byte[] bandHeaderBytes = new byte[BAND_HEADER_LENGTH];
        ByteArrayInputStream bandHeaderSource = new ByteArrayInputStream(bandHeaderBytes);
        BufferedInputStream bandHeaderStream = new BufferedInputStream(bandHeaderSource);

        CodecEncoding.getCodec(POPULATION_CODEC_WITH_EXPLICIT_CODECS, bandHeaderStream, defaultCodec);

        assertEquals(0, bandHeaderSource.available());
    }
}
