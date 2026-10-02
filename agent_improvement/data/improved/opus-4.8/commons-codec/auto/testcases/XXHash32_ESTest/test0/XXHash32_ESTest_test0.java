package org.apache.commons.codec.digest;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class XXHash32_ESTest_test0 extends XXHash32_ESTest_scaffolding {

    /**
     * A zero-length update is a no-op (XXHash32.update returns early when len <= 0),
     * so the checksum stays at the value xxHash32 produces for empty input with seed 0.
     */
    @Test(timeout = 4000)
    public void updateWithZeroLengthLeavesChecksumAtEmptyInputValue() throws Throwable {
        final int seed = 0;
        XXHash32 hash = new XXHash32(seed);

        byte[] data = new byte[20];
        int offset = 16;
        int length = 0;
        hash.update(data, offset, length);

        final long emptyInputChecksum = 46947589L;
        assertEquals(emptyInputChecksum, hash.getValue());
    }
}
