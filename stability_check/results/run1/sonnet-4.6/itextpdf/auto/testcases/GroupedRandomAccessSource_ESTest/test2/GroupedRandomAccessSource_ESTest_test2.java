package com.itextpdf.text.io;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class GroupedRandomAccessSource_ESTest_test2 extends GroupedRandomAccessSource_ESTest_scaffolding {

    private static final int SOURCE_COUNT = 8;
    private static final int BYTES_PER_SOURCE = 8;
    // Negative offset and length trigger early-exit: get() returns -1
    private static final int INVALID_OFFSET = (int) (byte) (-126);
    private static final int INVALID_LENGTH = (int) (byte) (-43);

    /**
     * Verifies that GroupedRandomAccessSource.get() returns -1 when called
     * with a negative offset, and that the total length equals the sum of all
     * constituent source lengths (8 sources × 8 bytes = 64).
     */
    @Test(timeout = 4000)
    public void test2() throws Throwable {
        // Build a shared data buffer that backs the array-based source
        byte[] sharedData = new byte[BYTES_PER_SOURCE];

        // Create reusable source wrappers over the same byte array
        ArrayRandomAccessSource arraySource = new ArrayRandomAccessSource(sharedData);
        GetBufferedRandomAccessSource bufferedSource = new GetBufferedRandomAccessSource(arraySource);
        IndependentRandomAccessSource independentSource = new IndependentRandomAccessSource(bufferedSource);

        // Populate the source array, mixing the three wrapper types across 8 slots
        RandomAccessSource[] sources = new RandomAccessSource[SOURCE_COUNT];
        sources[0] = bufferedSource;
        sources[1] = arraySource;
        sources[2] = bufferedSource;
        sources[3] = bufferedSource;
        sources[4] = independentSource;
        sources[5] = bufferedSource;
        sources[6] = arraySource;
        sources[7] = independentSource;

        GroupedRandomAccessSource groupedSource = new GroupedRandomAccessSource(sources);

        // A negative offset causes get() to return -1 without reading any bytes
        int bytesRead = groupedSource.get(0L, sharedData, INVALID_OFFSET, INVALID_LENGTH);
        assertEquals(-1, bytesRead);

        // Total length = SOURCE_COUNT * BYTES_PER_SOURCE = 8 * 8 = 64
        assertEquals(64L, groupedSource.length());
    }
}
