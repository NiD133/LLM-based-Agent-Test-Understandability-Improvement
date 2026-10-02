package org.apache.commons.compress.utils;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ByteUtils_ESTest_test11 extends ByteUtils_ESTest_scaffolding {

    // The maximum number of bytes that can be read into a long is 8.
    // Passing an 18-byte array exceeds this limit and must throw IllegalArgumentException.
    private static final int OVERSIZED_BYTE_COUNT = 18;

    @Test(timeout = 4000)
    public void fromLittleEndian_throwsWhenByteArrayExceedsEightBytes() throws Throwable {
        byte[] oversizedByteArray = new byte[OVERSIZED_BYTE_COUNT];

        try {
            ByteUtils.fromLittleEndian(oversizedByteArray);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            verifyException("org.apache.commons.compress.utils.ByteUtils", e);
        }
    }
}
