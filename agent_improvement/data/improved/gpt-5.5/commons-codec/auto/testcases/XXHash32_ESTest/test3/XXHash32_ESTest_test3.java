package org.apache.commons.codec.digest;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class XXHash32_ESTest_test3 extends XXHash32_ESTest_scaffolding {

    private static final long DEFAULT_HASH_VALUE_WITH_NO_INPUT = 46947589L;

    @Test(timeout = 4000)
    public void test3() throws Throwable {
        XXHash32 defaultHash = new XXHash32();

        long hashValue = defaultHash.getValue();

        assertEquals("Default XXHash32 value before any update should match the original seed-derived hash",
                DEFAULT_HASH_VALUE_WITH_NO_INPUT, hashValue);
    }
}
