package org.apache.commons.codec.digest;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class MurmurHash2_ESTest_test01 extends MurmurHash2_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test01_hash64_shortString_returnsExpectedHash() throws Throwable {
        long hash = MurmurHash2.hash64("BG7{/@,");
        assertEquals(8897355786490055066L, hash);
    }
}
