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

    /**
     * Verifies that hash32(byte[], int, int) throws NullPointerException when the data array is null.
     * The method cannot compute a hash without a valid byte array, regardless of the length and seed values.
     */
    @Test(timeout = 4000)
    public void test00_hash32WithNullByteArray_throwsNullPointerException() throws Throwable {
        byte[] nullData = null;
        int length = -65;
        int seed = -65;

        try {
            MurmurHash2.hash32(nullData, length, seed);
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException e) {
            verifyException("org.apache.commons.codec.digest.MurmurHash2", e);
        }
    }
}
