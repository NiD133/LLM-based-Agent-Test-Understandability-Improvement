package org.apache.commons.codec.digest;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class MurmurHash2_ESTest_test10 extends MurmurHash2_ESTest_scaffolding {

    /**
     * Verifies that hashing an empty substring (from index 0 with length 0) of an empty
     * string produces the expected deterministic 32-bit hash value using the default seed.
     */
    @Test(timeout = 4000)
    public void test_hash32_emptySubstring_returnsExpectedHash() throws Throwable {
        int hashOfEmptySubstring = MurmurHash2.hash32("", 0, 0);
        assertEquals(275646681, hashOfEmptySubstring);
    }
}
