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
     * Verifies that hash64(String) returns the expected 64-bit MurmurHash2 value
     * for a fixed input string, using the default seed applied to the UTF-8 bytes.
     */
    @Test(timeout = 4000)
    public void hash64OfStringReturnsExpectedValue() throws Throwable {
        final String input = "y\"1B>9T<!hw2,E^U'vm";
        final long expectedHash = -1450341502340637772L;

        final long actualHash = MurmurHash2.hash64(input);

        assertEquals(expectedHash, actualHash);
    }
}
