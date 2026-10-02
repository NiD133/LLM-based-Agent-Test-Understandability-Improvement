package org.apache.commons.codec.digest;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class XXHash32_ESTest_test1 extends XXHash32_ESTest_scaffolding {

    private static final int ZERO_SEED = 0;
    private static final int SINGLE_ZERO_BYTE = 0;
    private static final long EXPECTED_HASH_FOR_SINGLE_ZERO_BYTE = 3479547966L;

    @Test(timeout = 4000)
    public void test1() throws Throwable {
        XXHash32 hash = new XXHash32(ZERO_SEED);

        hash.update(SINGLE_ZERO_BYTE);

        long computedHash = hash.getValue();
        assertEquals(EXPECTED_HASH_FOR_SINGLE_ZERO_BYTE, computedHash);
    }
}
