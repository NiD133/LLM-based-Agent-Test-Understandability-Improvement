package org.apache.commons.codec.digest;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class XXHash32_ESTest_test1 extends XXHash32_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test1() throws Throwable {
        XXHash32 xXHash32_0 = new XXHash32(0);
        xXHash32_0.update(0);
        long long0 = xXHash32_0.getValue();
        assertEquals(3479547966L, long0);
    }
}
