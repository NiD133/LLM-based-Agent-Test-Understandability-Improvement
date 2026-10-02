package org.apache.commons.codec.digest;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class XXHash32_ESTest_test0 extends XXHash32_ESTest_scaffolding {

    private static final int SEED = 0;
    private static final int BUFFER_LENGTH = 20;
    private static final int OFFSET_AT_END_OF_UNUSED_SLICE = 16;
    private static final int ZERO_LENGTH_UPDATE = 0;
    private static final long HASH_VALUE_AFTER_NO_BYTES_ARE_ADDED = 46947589L;

    @Test(timeout = 4000)
    public void test0() throws Throwable {
        XXHash32 checksum = new XXHash32(SEED);
        byte[] buffer = new byte[BUFFER_LENGTH];

        checksum.update(buffer, (int) (byte) OFFSET_AT_END_OF_UNUSED_SLICE, ZERO_LENGTH_UPDATE);

        assertEquals(HASH_VALUE_AFTER_NO_BYTES_ARE_ADDED, checksum.getValue());
    }
}
