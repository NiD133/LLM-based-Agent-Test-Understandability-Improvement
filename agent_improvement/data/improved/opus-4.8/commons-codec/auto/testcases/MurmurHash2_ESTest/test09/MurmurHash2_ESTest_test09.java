package org.apache.commons.codec.digest;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class MurmurHash2_ESTest_test09 extends MurmurHash2_ESTest_scaffolding {

    /**
     * Verifies that {@link MurmurHash2#hash32(String)} returns the expected
     * 32-bit hash for a fixed input string, confirming the hash is stable for
     * a given input.
     */
    @Test(timeout = 4000)
    public void hash32OfStringReturnsExpectedValue() throws Throwable {
        int actualHash = MurmurHash2.hash32("^{MC\"");

        int expectedHash = -817914152;
        assertEquals(expectedHash, actualHash);
    }
}
