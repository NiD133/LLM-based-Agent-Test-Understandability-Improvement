package com.itextpdf.text.io;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class GroupedRandomAccessSource_ESTest_test4 extends GroupedRandomAccessSource_ESTest_scaffolding {

    /**
     * A GroupedRandomAccessSource concatenates several underlying sources end to end.
     * This test groups eight sources, each backed by a 7-byte (all-zero) buffer, and
     * verifies that:
     *   - the group's total length is the sum of the parts (8 * 7 = 56), and
     *   - reading the very first byte returns 0 (the default value of an empty byte array).
     */
    @Test(timeout = 4000)
    public void test4() throws Throwable {
        final int BYTES_PER_SOURCE = 7;
        final int SOURCE_COUNT = 8;

        // All eight grouped sources share the same 7-byte zero-filled backing buffer.
        byte[] backingBuffer = new byte[BYTES_PER_SOURCE];
        ArrayRandomAccessSource arraySource = new ArrayRandomAccessSource(backingBuffer);
        GetBufferedRandomAccessSource bufferedSource = new GetBufferedRandomAccessSource(arraySource);
        IndependentRandomAccessSource independentSource = new IndependentRandomAccessSource(bufferedSource);

        // Build a mix of the three wrapper types; only their lengths matter for the assertions.
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

        int firstByte = groupedSource.get(0L);

        assertEquals(SOURCE_COUNT * BYTES_PER_SOURCE, groupedSource.length());
        assertEquals(0, firstByte);
    }
}
