package org.apache.commons.codec.digest;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class MurmurHash2_ESTest_test12 extends MurmurHash2_ESTest_scaffolding {

    /**
     * Verifies the 64-bit hash of an empty string. With no input bytes the
     * result is determined entirely by the default seed mixing, yielding a
     * fixed, known constant.
     */
    @Test(timeout = 4000)
    public void hash64OfEmptyStringReturnsKnownConstant() throws Throwable {
        long actualHash = MurmurHash2.hash64("");

        long expectedHash = -7207201254813729732L;
        assertEquals(expectedHash, actualHash);
    }
}
