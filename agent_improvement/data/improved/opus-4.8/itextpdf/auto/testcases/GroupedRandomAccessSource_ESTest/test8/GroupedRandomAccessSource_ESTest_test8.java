package com.itextpdf.text.io;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class GroupedRandomAccessSource_ESTest_test8 extends GroupedRandomAccessSource_ESTest_scaffolding {

    /**
     * Reading into a destination buffer with an offset and length that exceed the
     * buffer's bounds must fail with an ArrayIndexOutOfBoundsException raised by the
     * underlying ArrayRandomAccessSource.
     */
    @Test(timeout = 4000)
    public void readPastDestinationBufferBoundsThrowsArrayIndexOutOfBounds() throws Throwable {
        // Build a grouped source backed by two views over the same 6-byte array.
        byte[] sixByteBuffer = new byte[6];
        ArrayRandomAccessSource backingSource = new ArrayRandomAccessSource(sixByteBuffer);
        RandomAccessSource[] sources = new RandomAccessSource[2];
        sources[0] = backingSource;
        sources[1] = backingSource;
        GroupedRandomAccessSource groupedSource = new GroupedRandomAccessSource(sources);

        // Reading 5 bytes into the buffer starting at destination offset 5 runs past
        // the end of the 6-byte buffer, so an unchecked exception is expected.
        long readPosition = 5L;
        int destinationOffset = 5;
        int bytesToRead = 5;
        try {
            groupedSource.get(readPosition, sixByteBuffer, destinationOffset, bytesToRead);
            fail("Expecting exception: ArrayIndexOutOfBoundsException");
        } catch (ArrayIndexOutOfBoundsException e) {
            // The exception carries no message (getMessage() returns null) and is
            // thrown from deep inside ArrayRandomAccessSource.
            verifyException("com.itextpdf.text.io.ArrayRandomAccessSource", e);
        }
    }
}
