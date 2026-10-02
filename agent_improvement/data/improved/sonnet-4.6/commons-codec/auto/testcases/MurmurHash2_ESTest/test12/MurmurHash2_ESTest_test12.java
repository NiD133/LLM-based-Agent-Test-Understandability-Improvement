package org.apache.commons.codec.digest;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class MurmurHash2_ESTest_test12 extends MurmurHash2_ESTest_scaffolding {

    // MurmurHash2 hash64 of an empty string uses the default seed (0xe17a1465)
    // and produces a deterministic 64-bit value.
    @Test(timeout = 4000)
    public void test_hash64_emptyString_returnsExpectedHash() throws Throwable {
        long hash = MurmurHash2.hash64("");
        assertEquals(-7207201254813729732L, hash);
    }
}
