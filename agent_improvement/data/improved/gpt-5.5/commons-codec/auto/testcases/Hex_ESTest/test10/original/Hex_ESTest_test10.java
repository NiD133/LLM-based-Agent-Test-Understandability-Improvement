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
public class Hex_ESTest_test10 extends Hex_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test10() throws Throwable {
        byte[] byteArray0 = new byte[9];
        String string0 = Hex.encodeHexString(byteArray0, false);
        assertEquals("000000000000000000", string0);
    }
}
