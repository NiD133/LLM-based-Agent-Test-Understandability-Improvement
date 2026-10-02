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

    @Test(timeout = 4000)
    public void test09() throws Throwable {
        final int populationCodecEncoding = 141;
        final int headerByteCount = 9;
        final BHSDCodec defaultCodec = Codec.CHAR3;
        final byte[] bandHeaderBytes = new byte[headerByteCount];
        final ByteArrayInputStream bandHeaderInput = new ByteArrayInputStream(bandHeaderBytes);
        final BufferedInputStream bufferedBandHeaderInput = new BufferedInputStream(bandHeaderInput);

        CodecEncoding.getCodec(populationCodecEncoding, bufferedBandHeaderInput, defaultCodec);

        assertEquals(0, bandHeaderInput.available());
    }
}
