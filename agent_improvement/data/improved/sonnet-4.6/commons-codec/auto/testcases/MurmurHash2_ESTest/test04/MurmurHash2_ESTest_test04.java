package org.apache.commons.codec.digest;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class MurmurHash2_ESTest_test04 extends MurmurHash2_ESTest_scaffolding {

    // MurmurHash2 produces a deterministic 64-bit hash for a given string input.
    // This test verifies the expected hash value for a specific 19-character ASCII string.
    @Test(timeout = 4000)
    public void test04_hash64_knownStringProducesExpectedHash() throws Throwable {
        String input = "y\"1B>9T<!hw2,E^U'vm";
        long expectedHash = -1450341502340637772L;

        long actualHash = MurmurHash2.hash64(input);

        assertEquals(expectedHash, actualHash);
    }
}
