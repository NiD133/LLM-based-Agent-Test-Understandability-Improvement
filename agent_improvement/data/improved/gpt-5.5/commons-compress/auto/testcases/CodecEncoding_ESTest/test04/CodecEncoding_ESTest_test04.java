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
public class CodecEncoding_ESTest_test04 extends CodecEncoding_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test04() throws Throwable {
        BHSDCodec defaultCodec = Codec.CHAR3;
        byte[] bandHeaderBytes = new byte[9];
        bandHeaderBytes[0] = (byte) 93;
        ByteArrayInputStream bandHeaderInput = new ByteArrayInputStream(bandHeaderBytes);

        Codec decodedCodec = CodecEncoding.getCodec(116, bandHeaderInput, defaultCodec);
        int[] codecSpecifier = CodecEncoding.getSpecifier(decodedCodec, decodedCodec);

        assertEquals(7, bandHeaderInput.available());
        assertArrayEquals(new int[] { 116, 29, 0 }, codecSpecifier);
    }
}
