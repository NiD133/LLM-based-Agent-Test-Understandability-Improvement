package org.apache.commons.codec.digest;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class XXHash32_ESTest_test4 extends XXHash32_ESTest_scaffolding {

    private static final int ZERO_SEED = 0;
    private static final long EMPTY_HASH_VALUE = 46947589L;

    @Test(timeout = 4000)
    public void test4() throws Throwable {
        XXHash32 hash = new XXHash32(ZERO_SEED);

        hash.reset();

        assertEquals(EMPTY_HASH_VALUE, hash.getValue());
    }
}
