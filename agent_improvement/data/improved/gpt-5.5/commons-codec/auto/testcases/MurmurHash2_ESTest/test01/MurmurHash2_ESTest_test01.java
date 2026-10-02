package org.apache.commons.codec.digest;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class MurmurHash2_ESTest_test01 extends MurmurHash2_ESTest_scaffolding {

    private static final String HASH_INPUT = "BG7{/@,";
    private static final long EXPECTED_64_BIT_HASH = 8897355786490055066L;

    @Test(timeout = 4000)
    public void test01() throws Throwable {
        long actualHash = MurmurHash2.hash64(HASH_INPUT);

        assertEquals(EXPECTED_64_BIT_HASH, actualHash);
    }
}
