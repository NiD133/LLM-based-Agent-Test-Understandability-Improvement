package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Hex_ESTest_test06 extends Hex_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test06() throws Throwable {
        char[] charArray0 = new char[6];
        byte[] byteArray0 = new byte[7];
        try {
            Hex.decodeHex(charArray0, byteArray0, 1589);
            fail("Expecting exception: Exception");
        } catch (Exception e) {
            //
            // Output array is not large enough to accommodate decoded data.
            //
            verifyException("org.apache.commons.codec.binary.Hex", e);
        }
    }
}
