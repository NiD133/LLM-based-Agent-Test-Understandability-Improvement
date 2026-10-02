package org.apache.commons.codec.digest;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class MurmurHash2_ESTest_test04 extends MurmurHash2_ESTest_scaffolding {

    /**
     * Verifies that {@link MurmurHash2#hash64(String)} produces the expected
     * 64-bit hash (using the default seed and UTF-8 encoding) for a fixed input
     * string containing punctuation and mixed-case characters.
     */
    @Test(timeout = 4000)
    public void hash64OfString_returnsExpectedValue() throws Throwable {
        final String input = "y\"1B>9T<!hw2,E^U'vm";
        final long expectedHash = -1450341502340637772L;

        final long actualHash = MurmurHash2.hash64(input);

        assertEquals(expectedHash, actualHash);
    }
}
