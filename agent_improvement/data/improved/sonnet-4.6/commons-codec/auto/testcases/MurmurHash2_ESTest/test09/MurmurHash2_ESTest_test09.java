package org.apache.commons.codec.digest;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class MurmurHash2_ESTest_test09 extends MurmurHash2_ESTest_scaffolding {

    // Expected MurmurHash2 32-bit hash of the string "^{MC\""
    private static final int EXPECTED_HASH_OF_SPECIAL_CHAR_STRING = -817914152;

    @Test(timeout = 4000)
    public void test_hash32_withSpecialCharacterString_returnsExpectedHash() throws Throwable {
        int actualHash = MurmurHash2.hash32("^{MC\"");
        assertEquals(EXPECTED_HASH_OF_SPECIAL_CHAR_STRING, actualHash);
    }
}
