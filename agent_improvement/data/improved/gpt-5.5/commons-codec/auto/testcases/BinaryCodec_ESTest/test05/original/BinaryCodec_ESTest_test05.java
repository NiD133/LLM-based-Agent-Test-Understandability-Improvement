package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class BinaryCodec_ESTest_test05 extends BinaryCodec_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test05() throws Throwable {
        BinaryCodec binaryCodec0 = new BinaryCodec();
        byte[] byteArray0 = new byte[2];
        byteArray0[0] = (byte) (-1);
        byte[] byteArray1 = binaryCodec0.encode(byteArray0);
        assertEquals(16, byteArray1.length);
    }
}
