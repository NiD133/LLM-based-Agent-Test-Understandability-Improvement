package org.apache.commons.codec.digest;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class XXHash32_ESTest_test6 extends XXHash32_ESTest_scaffolding {

    /**
     * Hashing 16 zero bytes (a single full 16-byte block) with the default
     * seed of 0 produces a known, stable xxHash32 checksum.
     */
    @Test(timeout = 4000)
    public void hashingSixteenZeroBytesReturnsExpectedChecksum() throws Throwable {
        XXHash32 hash = new XXHash32();

        byte[] data = new byte[22];
        int offset = 0;
        int length = 16;
        hash.update(data, offset, length);

        long expectedChecksum = 2382506810L;
        assertEquals(expectedChecksum, hash.getValue());
    }
}
