package org.apache.commons.codec.net;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class PercentCodec_ESTest_test09 extends PercentCodec_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test09() throws Throwable {
        byte[] byteArray0 = new byte[1];
        byteArray0[0] = (byte) 43;
        PercentCodec percentCodec0 = new PercentCodec(byteArray0, true);
        byte[] byteArray1 = percentCodec0.decode(byteArray0);
        assertArrayEquals(new byte[] { (byte) 32 }, byteArray1);
    }
}
