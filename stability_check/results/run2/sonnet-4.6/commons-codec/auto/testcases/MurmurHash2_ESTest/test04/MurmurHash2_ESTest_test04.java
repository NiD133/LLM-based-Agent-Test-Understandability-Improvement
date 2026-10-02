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

    // Expected 64-bit MurmurHash2 value for the test input string
    private static final long EXPECTED_HASH_OF_SPECIAL_CHARS_INPUT = -1450341502340637772L;

    @Test(timeout = 4000)
    public void test_hash64_withSpecialCharacterString_returnsExpectedHash() throws Throwable {
        String inputWithSpecialChars = "y\"1B>9T<!hw2,E^U'vm";

        long actualHash = MurmurHash2.hash64(inputWithSpecialChars);

        assertEquals(EXPECTED_HASH_OF_SPECIAL_CHARS_INPUT, actualHash);
    }
}
