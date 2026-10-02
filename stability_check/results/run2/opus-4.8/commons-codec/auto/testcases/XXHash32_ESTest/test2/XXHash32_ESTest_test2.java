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
     * Feeds four single bytes (13, 13, 0, 0) into an XXHash32 created with a
     * seed of 0 and verifies the resulting checksum value.
     */
    @Test(timeout = 4000)
    public void updateWithFourBytesProducesExpectedChecksum() throws Throwable {
        XXHash32 hash = new XXHash32(0);

        hash.update(13);
        hash.update(13);
        hash.update((byte) 0);
        hash.update((byte) 0);

        long expectedChecksum = 2114005244L;
        assertEquals(expectedChecksum, hash.getValue());
    }
}
