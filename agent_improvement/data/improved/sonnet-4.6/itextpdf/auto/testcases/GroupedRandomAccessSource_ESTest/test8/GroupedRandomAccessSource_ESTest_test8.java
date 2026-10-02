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
     * Verifies that reading beyond the end of the source array throws ArrayIndexOutOfBoundsException.
     *
     * Setup: a GroupedRandomAccessSource backed by two ArrayRandomAccessSource instances,
     * each wrapping the same 6-byte array. The read is attempted at offset 5 into the
     * grouped source, requesting 5 bytes into a destination buffer that only has room
     * starting at index 5 — which exceeds the 6-element buffer boundary.
     */
    @Test(timeout = 4000)
    public void test8_readBeyondBufferBoundaryThrowsArrayIndexOutOfBoundsException() throws Throwable {
        // Arrange: a 6-byte source array shared by two ArrayRandomAccessSource instances
        byte[] sourceData = new byte[6];
        ArrayRandomAccessSource sourceA = new ArrayRandomAccessSource(sourceData);

        RandomAccessSource[] sources = new RandomAccessSource[2];
        sources[0] = (RandomAccessSource) sourceA;
        sources[1] = (RandomAccessSource) sourceA;

        GroupedRandomAccessSource groupedSource = new GroupedRandomAccessSource(sources);

        // Act & Assert: reading 5 bytes starting at destination offset 5 exceeds the 6-element buffer
        try {
            groupedSource.get((long) 5, sourceData, 5, 5);
            fail("Expecting exception: ArrayIndexOutOfBoundsException");
        } catch (ArrayIndexOutOfBoundsException e) {
            // No detail message is expected from the underlying array access
            verifyException("com.itextpdf.text.io.ArrayRandomAccessSource", e);
        }
    }
}
