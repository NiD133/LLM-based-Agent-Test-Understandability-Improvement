package org.apache.commons.compress.archivers.zip;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class X5455_ExtendedTimestamp_ESTest_test09 extends X5455_ExtendedTimestamp_ESTest_scaffolding {

    /**
     * When the flags byte advertises a create-time field but the backing buffer
     * is too small to hold the 4 timestamp bytes that follow, parsing should
     * fail while reading past the end of the array.
     *
     * <p>The 3-byte buffer has the create-time bit (value 4) set at index 2.
     * Parsing starts at offset 2, so that byte is read as the flags. With a
     * declared length far larger than the buffer, the parser then tries to read
     * 4 create-time bytes starting at index 3, which is out of bounds.</p>
     */
    @Test(timeout = 4000)
    public void parseWithCreateTimeBitButTruncatedBufferThrowsArrayIndexOutOfBounds() throws Throwable {
        X5455_ExtendedTimestamp extendedTimestamp = new X5455_ExtendedTimestamp();

        // Buffer too short to actually contain the create-time field it advertises.
        byte[] buffer = new byte[3];
        buffer[2] = X5455_ExtendedTimestamp.CREATE_TIME_BIT; // flags byte read at offset 2

        int parseStartOffset = 2;
        int declaredLength = 25461; // far exceeds the real buffer size

        try {
            extendedTimestamp.parseFromCentralDirectoryData(buffer, parseStartOffset, declaredLength);
            fail("Expecting exception: ArrayIndexOutOfBoundsException");
        } catch (ArrayIndexOutOfBoundsException e) {
            // Reading the create-time bytes runs off the end of the 3-byte buffer.
            verifyException("org.apache.commons.compress.utils.ByteUtils", e);
        }
    }
}
