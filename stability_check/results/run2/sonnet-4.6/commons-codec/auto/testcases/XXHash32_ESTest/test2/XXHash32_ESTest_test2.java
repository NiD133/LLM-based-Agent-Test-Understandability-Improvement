package org.apache.commons.codec.digest;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class XXHash32_ESTest_test2 extends XXHash32_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test2() throws Throwable {
        XXHash32 hasher = new XXHash32(0);
        hasher.update(13);
        hasher.update(13);
        hasher.update(0);
        hasher.update(0);
        long hashValue = hasher.getValue();
        assertEquals(2114005244L, hashValue);
    }
}
