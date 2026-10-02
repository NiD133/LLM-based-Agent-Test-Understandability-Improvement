package org.apache.commons.compress.utils;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ByteUtils_ESTest_test00 extends ByteUtils_ESTest_scaffolding {

    /**
     * Verifies that {@link ByteUtils#toLittleEndian(byte[], long, int, int)} throws an
     * {@link ArrayIndexOutOfBoundsException} when the requested number of bytes does not
     * fit into the target array. Here the destination holds 2 bytes but the call asks to
     * write 6 bytes starting at offset 0, so writing overruns the array.
     */
    @Test(timeout = 4000)
    public void toLittleEndian_writingMoreBytesThanArrayHolds_throwsArrayIndexOutOfBounds() throws Throwable {
        byte[] destination = new byte[2];
        long value = -1L;
        int offset = 0;
        int lengthExceedingArray = 6;

        try {
            ByteUtils.toLittleEndian(destination, value, offset, lengthExceedingArray);
            fail("Expected ArrayIndexOutOfBoundsException because 6 bytes do not fit into a 2-byte array");
        } catch (ArrayIndexOutOfBoundsException e) {
            verifyException("org.apache.commons.compress.utils.ByteUtils", e);
        }
    }
}
