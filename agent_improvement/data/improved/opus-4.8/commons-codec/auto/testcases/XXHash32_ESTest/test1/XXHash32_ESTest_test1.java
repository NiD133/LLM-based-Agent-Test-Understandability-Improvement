package org.apache.commons.codec.digest;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class XXHash32_ESTest_test1 extends XXHash32_ESTest_scaffolding {

    /**
     * Hashing a single zero byte with a seed of 0 should yield the well-known
     * xxHash32 checksum for that input.
     */
    @Test(timeout = 4000)
    public void updateSingleZeroByteProducesExpectedChecksum() throws Throwable {
        XXHash32 hash = new XXHash32(0);

        hash.update(0);

        long checksum = hash.getValue();
        assertEquals(3479547966L, checksum);
    }
}
