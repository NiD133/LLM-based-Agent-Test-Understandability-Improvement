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
     * Feeds four bytes (13, 13, 0, 0) one at a time into an XXHash32 instance
     * seeded with 0, then verifies the resulting 32-bit checksum.
     */
    @Test(timeout = 4000)
    public void updatingFourSingleBytesProducesExpectedChecksum() throws Throwable {
        XXHash32 hash = new XXHash32(0);

        hash.update(13);
        hash.update(13);
        hash.update((byte) 0);
        hash.update((byte) 0);

        long checksum = hash.getValue();

        assertEquals(2114005244L, checksum);
    }
}
