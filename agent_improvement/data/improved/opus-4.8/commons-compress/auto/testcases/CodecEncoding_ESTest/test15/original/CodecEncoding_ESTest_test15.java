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

    @Test(timeout = 4000)
    public void test15() throws Throwable {
        BHSDCodec bHSDCodec0 = CodecEncoding.getCanonicalCodec(13);
        byte[] byteArray0 = new byte[1];
        ByteArrayInputStream byteArrayInputStream0 = new ByteArrayInputStream(byteArray0);
        Codec codec0 = CodecEncoding.getCodec(128, byteArrayInputStream0, bHSDCodec0);
        int[] intArray0 = CodecEncoding.getSpecifier(codec0, codec0.UNSIGNED5);
        assertEquals(0, byteArrayInputStream0.available());
        assertArrayEquals(new int[] { 123, 63, 13, 13 }, intArray0);
    }
}
