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

    @Test(timeout = 4000)
    public void test11() throws Throwable {
        BHSDCodec bHSDCodec0 = CodecEncoding.getCanonicalCodec(13);
        byte[] byteArray0 = new byte[2];
        ByteArrayInputStream byteArrayInputStream0 = new ByteArrayInputStream(byteArray0);
        BHSDCodec bHSDCodec1 = (BHSDCodec) CodecEncoding.getCodec(2, byteArrayInputStream0, bHSDCodec0);
        assertEquals(4294967293L, bHSDCodec0.largest());
        assertEquals((-128L), bHSDCodec1.smallest());
    }
}
