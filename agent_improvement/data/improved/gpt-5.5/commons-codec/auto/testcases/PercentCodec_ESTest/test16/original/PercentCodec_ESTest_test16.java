package org.apache.commons.codec.net;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class PercentCodec_ESTest_test16 extends PercentCodec_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test16() throws Throwable {
        PercentCodec percentCodec0 = new PercentCodec();
        byte[] byteArray0 = new byte[1];
        byteArray0[0] = (byte) (-59);
        byte[] byteArray1 = percentCodec0.encode(byteArray0);
        assertArrayEquals(new byte[] { (byte) 37, (byte) 67, (byte) 53 }, byteArray1);
    }
}
