package org.apache.commons.codec.digest;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class MurmurHash2_ESTest_test02 extends MurmurHash2_ESTest_scaffolding {

    /**
     * Verifies that hash64(String) produces the correct 64-bit MurmurHash2 value
     * for a 14-character ASCII input containing special characters.
     */
    @Test(timeout = 4000)
    public void test_hash64_string_withSpecialCharacters_returnsExpectedHash() throws Throwable {
        String input = "*abc_PldN~tL$r";
        long expectedHash = 1862178600039130994L;

        long actualHash = MurmurHash2.hash64(input);

        assertEquals(expectedHash, actualHash);
    }
}
