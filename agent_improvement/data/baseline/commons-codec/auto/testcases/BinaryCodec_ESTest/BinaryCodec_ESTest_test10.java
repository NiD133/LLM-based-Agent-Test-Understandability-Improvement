package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class BinaryCodec_ESTest_test10 extends BinaryCodec_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test10() throws Throwable {
        BinaryCodec binaryCodec0 = new BinaryCodec();
        byte[] byteArray0 = new byte[8];
        byteArray0[0] = (byte) 49;
        byte[] byteArray1 = binaryCodec0.decode(byteArray0);
        assertArrayEquals(new byte[] { (byte) (-128) }, byteArray1);
    }
}
