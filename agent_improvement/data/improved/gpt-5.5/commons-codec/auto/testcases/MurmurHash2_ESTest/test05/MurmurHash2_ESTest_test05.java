package org.apache.commons.codec.digest;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class MurmurHash2_ESTest_test05 extends MurmurHash2_ESTest_scaffolding {

    private static final String HASH_INPUT = "M78]F%@JR*";
    private static final long EXPECTED_HASH64 = 8542654808481837325L;

    @Test(timeout = 4000)
    public void test05() throws Throwable {
        long actualHash64 = MurmurHash2.hash64(HASH_INPUT);

        assertEquals(EXPECTED_HASH64, actualHash64);
    }
}
