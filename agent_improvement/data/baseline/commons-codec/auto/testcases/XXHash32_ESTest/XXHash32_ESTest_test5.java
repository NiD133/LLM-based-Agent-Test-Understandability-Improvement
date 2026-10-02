package org.apache.commons.codec.digest;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class XXHash32_ESTest_test5 extends XXHash32_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test5() throws Throwable {
        XXHash32 xXHash32_0 = new XXHash32();
        xXHash32_0.update((int) (byte) 109);
        byte[] byteArray0 = new byte[22];
        xXHash32_0.update(byteArray0, 0, (int) (byte) 16);
        assertEquals(1174888648L, xXHash32_0.getValue());
    }
}
