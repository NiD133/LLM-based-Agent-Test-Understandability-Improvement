package org.apache.commons.codec.digest;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class MurmurHash2_ESTest_test08 extends MurmurHash2_ESTest_scaffolding {

    /**
     * Verifies that {@link MurmurHash2#hash32(String)} produces the documented,
     * stable 32-bit hash for the input string "Bl" (using the default seed and
     * UTF-8 encoding).
     */
    @Test(timeout = 4000)
    public void hash32OfStringReturnsExpectedValue() throws Throwable {
        final int EXPECTED_HASH = -504122062;

        int actualHash = MurmurHash2.hash32("Bl");

        assertEquals(EXPECTED_HASH, actualHash);
    }
}
