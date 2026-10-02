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
        BHSDCodec bHSDCodec0 = Codec.CHAR3;
        byte[] byteArray0 = new byte[9];
        ByteArrayInputStream byteArrayInputStream0 = new ByteArrayInputStream(byteArray0);
        BufferedInputStream bufferedInputStream0 = new BufferedInputStream(byteArrayInputStream0);
        CodecEncoding.getCodec(141, bufferedInputStream0, bHSDCodec0);
        assertEquals(0, byteArrayInputStream0.available());
    }
}
