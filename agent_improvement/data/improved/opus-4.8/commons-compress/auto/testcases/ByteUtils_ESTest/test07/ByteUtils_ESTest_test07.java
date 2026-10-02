package org.apache.commons.compress.utils;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.DataInput;
import java.io.DataInputStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ByteUtils_ESTest_test07 extends ByteUtils_ESTest_scaffolding {

    /**
     * Verifies that {@link ByteUtils#fromLittleEndian(DataInput, int)} reads exactly the
     * requested number of bytes from the underlying input and decodes them as a long.
     *
     * <p>The source is a window of two zero bytes inside a larger array. Reading a single
     * byte yields 0 and leaves one byte still available in the stream.</p>
     */
    @Test(timeout = 4000)
    public void readsSingleZeroByteAsLittleEndianLong() throws Throwable {
        // A 7-byte all-zero buffer, exposed through a 2-byte window starting at offset 2.
        byte[] buffer = new byte[7];
        int windowOffset = 2;
        int windowLength = 2;
        ByteArrayInputStream windowedInput = new ByteArrayInputStream(buffer, windowOffset, windowLength);
        DataInputStream dataInput = new DataInputStream(windowedInput);

        // Read a single little-endian byte from the stream.
        int bytesToRead = 1;
        long value = ByteUtils.fromLittleEndian((DataInput) dataInput, bytesToRead);

        assertEquals(0L, value);
        // One byte consumed out of the two available, so one remains.
        assertEquals(1, windowedInput.available());
    }
}
