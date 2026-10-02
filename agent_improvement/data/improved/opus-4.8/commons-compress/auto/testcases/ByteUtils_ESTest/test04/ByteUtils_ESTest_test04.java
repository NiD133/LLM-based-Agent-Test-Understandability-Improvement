package org.apache.commons.compress.utils;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ByteUtils_ESTest_test04 extends ByteUtils_ESTest_scaffolding {

    /**
     * Verifies that {@link ByteUtils#toLittleEndian(byte[], long, int, int)} writes
     * nothing when given a negative length. The loop in toLittleEndian only runs while
     * {@code i < length}, so a negative length skips it entirely and the target array
     * is left untouched (all zeros), regardless of the value or offset.
     */
    @Test(timeout = 4000)
    public void test04() throws Throwable {
        int negativeOffset = -1;
        int negativeLength = -1;
        long value = 4466L;

        byte[] target = new byte[5];
        ByteUtils.toLittleEndian(target, value, negativeOffset, negativeLength);

        byte[] expectedUntouched = new byte[] { 0, 0, 0, 0, 0 };
        assertArrayEquals(expectedUntouched, target);
    }
}
