package org.apache.commons.codec.digest;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class XXHash32_ESTest_test0 extends XXHash32_ESTest_scaffolding {

    // Expected XXHash32 value for seed=0 with no data processed (zero-length update)
    private static final long EXPECTED_HASH_FOR_EMPTY_INPUT_WITH_SEED_0 = 46947589L;

    @Test(timeout = 4000)
    public void test_getValue_returnsInitialHashWhenUpdateLengthIsZero() throws Throwable {
        // A zero-length update should be a no-op; getValue() should return the seed-derived initial hash
        XXHash32 hasher = new XXHash32(0);
        byte[] data = new byte[20];
        hasher.update(data, 16, 0);
        assertEquals(EXPECTED_HASH_FOR_EMPTY_INPUT_WITH_SEED_0, hasher.getValue());
    }
}
