package org.apache.commons.codec.digest;

import org.junit.Test;
import static org.junit.Assert.fail;
import static org.evosuite.runtime.EvoAssertions.verifyException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class MurmurHash2_ESTest_test00 extends MurmurHash2_ESTest_scaffolding {

    /**
     * hash32(byte[], int, int) must reject a null data array by throwing a
     * NullPointerException, since the algorithm dereferences the array while
     * mixing its bytes. The length and seed values are irrelevant here.
     */
    @Test(timeout = 4000)
    public void hash32WithNullDataThrowsNullPointerException() throws Throwable {
        final byte[] nullData = null;
        final int length = -65;
        final int seed = -65;

        try {
            MurmurHash2.hash32(nullData, length, seed);
            fail("Expected a NullPointerException for null input data");
        } catch (NullPointerException e) {
            // The exception originates from MurmurHash2 and carries no message.
            verifyException("org.apache.commons.codec.digest.MurmurHash2", e);
        }
    }
}
