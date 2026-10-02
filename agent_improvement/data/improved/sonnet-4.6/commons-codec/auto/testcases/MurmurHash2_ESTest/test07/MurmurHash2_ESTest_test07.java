package org.apache.commons.codec.digest;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class MurmurHash2_ESTest_test07 extends MurmurHash2_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test07_hash64_knownStringProducesExpectedHash() throws Throwable {
        // Verify that hash64(String) returns a deterministic 64-bit hash for a known input.
        // The expected value was pre-computed by the MurmurHash64A algorithm with its
        // default seed (0xe17a1465) applied to the UTF-8 bytes of the input string.
        long actualHash = MurmurHash2.hash64("G,5 2lXZ1083");
        assertEquals(-199748896782609694L, actualHash);
    }
}
