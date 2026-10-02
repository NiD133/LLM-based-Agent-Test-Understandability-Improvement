package org.apache.commons.codec.digest;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class MurmurHash2_ESTest_test06 extends MurmurHash2_ESTest_scaffolding {

    /**
     * Verifies that the 64-bit hash of the single-character string "v"
     * (using the default seed) matches the known expected value.
     */
    @Test(timeout = 4000)
    public void hash64OfSingleCharacterStringReturnsExpectedValue() throws Throwable {
        long actualHash = MurmurHash2.hash64("v");

        assertEquals(5594253894753466330L, actualHash);
    }
}
