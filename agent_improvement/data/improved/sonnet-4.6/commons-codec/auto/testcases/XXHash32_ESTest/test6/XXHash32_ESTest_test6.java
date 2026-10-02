package org.apache.commons.codec.digest;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class XXHash32_ESTest_test6 extends XXHash32_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test6() throws Throwable {
        XXHash32 hasher = new XXHash32();
        byte[] data = new byte[22];
        hasher.update(data, 0, 16);
        long hashValue = hasher.getValue();
        assertEquals(2382506810L, hashValue);
    }
}
