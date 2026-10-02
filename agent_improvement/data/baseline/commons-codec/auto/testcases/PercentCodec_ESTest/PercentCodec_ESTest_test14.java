package org.apache.commons.codec.net;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class PercentCodec_ESTest_test14 extends PercentCodec_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test14() throws Throwable {
        byte[] byteArray0 = new byte[5];
        PercentCodec percentCodec0 = new PercentCodec(byteArray0, true);
        byte[] byteArray1 = new byte[1];
        byteArray1[0] = (byte) 13;
        byte[] byteArray2 = percentCodec0.encode(byteArray1);
        assertSame(byteArray2, byteArray1);
        assertNotNull(byteArray2);
    }
}
