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

    // MurmurHash2.hash32(String) converts the input to UTF-8 bytes and applies
    // the 32-bit MurmurHash2 algorithm with the default seed (0x9747b28c).
    // For the two-character ASCII string "Bl" the expected hash is -504122062.
    @Test(timeout = 4000)
    public void test_hash32_shortAsciiString_returnsExpectedHash() throws Throwable {
        int hash = MurmurHash2.hash32("Bl");
        assertEquals(-504122062, hash);
    }
}
