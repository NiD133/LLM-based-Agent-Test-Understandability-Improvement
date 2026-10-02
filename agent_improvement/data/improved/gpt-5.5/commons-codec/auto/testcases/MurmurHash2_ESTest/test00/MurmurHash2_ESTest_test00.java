package org.apache.commons.codec.digest;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class MurmurHash2_ESTest_test00 extends MurmurHash2_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test00() throws Throwable {
        final byte[] nullInput = null;
        final int length = -65;
        final int seed = -65;

        try {
            MurmurHash2.hash32(nullInput, length, seed);
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException e) {
            verifyException("org.apache.commons.codec.digest.MurmurHash2", e);
        }
    }
}
