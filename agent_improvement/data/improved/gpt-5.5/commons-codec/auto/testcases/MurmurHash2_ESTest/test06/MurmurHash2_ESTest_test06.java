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

    @Test(timeout = 4000)
    public void test06() throws Throwable {
        long actualHash = MurmurHash2.hash64("v");

        assertEquals(5594253894753466330L, actualHash);
    }
}
