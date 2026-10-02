package org.apache.commons.codec.digest;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class MurmurHash2_ESTest_test06 extends MurmurHash2_ESTest_scaffolding {

    // Expected MurmurHash2 64-bit hash of the single-character string "v"
    private static final long EXPECTED_HASH64_OF_V = 5594253894753466330L;

    @Test(timeout = 4000)
    public void test_hash64_singleCharacterString_returnsExpectedHash() throws Throwable {
        long actualHash = MurmurHash2.hash64("v");
        assertEquals(EXPECTED_HASH64_OF_V, actualHash);
    }
}
