package org.apache.commons.codec.digest;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class XXHash32_ESTest_test2 extends XXHash32_ESTest_scaffolding {

    private static final int ZERO_SEED = 0;
    private static final int CARRIAGE_RETURN_BYTE = 13;
    private static final int NUL_BYTE = (int) (byte) 0;
    private static final long EXPECTED_CHECKSUM = 2114005244L;

    @Test(timeout = 4000)
    public void test2() throws Throwable {
        XXHash32 checksum = new XXHash32(ZERO_SEED);

        checksum.update(CARRIAGE_RETURN_BYTE);
        checksum.update(CARRIAGE_RETURN_BYTE);
        checksum.update(NUL_BYTE);
        checksum.update(NUL_BYTE);

        long actualChecksum = checksum.getValue();

        assertEquals(EXPECTED_CHECKSUM, actualChecksum);
    }
}
