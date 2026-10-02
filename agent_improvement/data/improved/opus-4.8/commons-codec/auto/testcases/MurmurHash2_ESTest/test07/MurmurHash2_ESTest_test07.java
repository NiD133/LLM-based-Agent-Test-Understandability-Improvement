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

    /**
     * Verifies that {@link MurmurHash2#hash64(String)} returns the expected
     * 64-bit hash for a fixed input string, using the method's default seed.
     */
    @Test(timeout = 4000)
    public void hash64OfStringReturnsExpectedValue() throws Throwable {
        final String input = "G,5 2lXZ1083";
        final long expectedHash = -199748896782609694L;

        final long actualHash = MurmurHash2.hash64(input);

        assertEquals(expectedHash, actualHash);
    }
}
