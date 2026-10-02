package org.apache.commons.codec.digest;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class XXHash32_ESTest_test2 extends XXHash32_ESTest_scaffolding {

    /**
     * Verifies that XXHash32 with seed 0 produces the expected hash after
     * feeding four single-byte updates: two bytes of value 13 followed by
     * two zero bytes.
     */
    @Test(timeout = 4000)
    public void test_hashWithSeedZero_fourByteUpdates_returnsExpectedValue() throws Throwable {
        XXHash32 hasher = new XXHash32(0);
        hasher.update(13);
        hasher.update(13);
        hasher.update(0);
        hasher.update(0);

        long hash = hasher.getValue();

        assertEquals(2114005244L, hash);
    }
}
