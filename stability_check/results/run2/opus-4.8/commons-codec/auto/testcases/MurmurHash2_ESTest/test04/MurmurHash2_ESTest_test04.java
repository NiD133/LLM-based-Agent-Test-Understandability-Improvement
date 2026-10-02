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

    /**
     * Verifies that {@link MurmurHash2#hash64(String)} returns the expected
     * 64-bit hash for a sample string, using the default seed applied
     * internally by the method.
     */
    @Test(timeout = 4000)
    public void hash64OfStringReturnsExpectedHash() throws Throwable {
        String input = "y\"1B>9T<!hw2,E^U'vm";
        long expectedHash = -1450341502340637772L;

        long actualHash = MurmurHash2.hash64(input);

        assertEquals(expectedHash, actualHash);
    }
}
