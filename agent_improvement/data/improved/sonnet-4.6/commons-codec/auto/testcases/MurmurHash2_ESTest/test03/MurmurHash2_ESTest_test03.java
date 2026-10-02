package org.apache.commons.codec.digest;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class MurmurHash2_ESTest_test03 extends MurmurHash2_ESTest_scaffolding {

    // Expected 64-bit MurmurHash2 value for the input string "'J-f"
    private static final long EXPECTED_HASH_OF_APOSTROPHE_J_DASH_F = 5768382861705900380L;

    @Test(timeout = 4000)
    public void test03_hash64_shortStringProducesExpectedValue() throws Throwable {
        long actualHash = MurmurHash2.hash64("'J-f");
        assertEquals(EXPECTED_HASH_OF_APOSTROPHE_J_DASH_F, actualHash);
    }
}
