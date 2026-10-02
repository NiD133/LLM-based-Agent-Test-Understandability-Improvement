package org.apache.commons.codec.digest;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class XXHash32_ESTest_test1 extends XXHash32_ESTest_scaffolding {

    // Verifies that hashing a single zero byte with seed 0 yields the expected XXHash32 checksum.
    @Test(timeout = 4000)
    public void test_hashSingleZeroByteWithSeedZero_returnsExpectedChecksum() throws Throwable {
        XXHash32 hasher = new XXHash32(/* seed */ 0);
        hasher.update(/* byteValue */ 0);
        long checksum = hasher.getValue();
        assertEquals(3479547966L, checksum);
    }
}
