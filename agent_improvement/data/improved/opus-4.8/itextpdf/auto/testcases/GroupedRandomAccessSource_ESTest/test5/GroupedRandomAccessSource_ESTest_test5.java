package com.itextpdf.text.io;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class GroupedRandomAccessSource_ESTest_test5 extends GroupedRandomAccessSource_ESTest_scaffolding {

    /** Number of bytes backing each individual source. */
    private static final int BYTES_PER_SOURCE = 6;

    /** Number of sources grouped together. */
    private static final int SOURCE_COUNT = 8;

    /**
     * A GroupedRandomAccessSource exposes the concatenation of its member sources, so its
     * total length is the sum of the member lengths (8 sources * 6 bytes = 48).
     *
     * Reading from a negative position is out of bounds, so get(...) returns -1 without
     * touching the destination buffer.
     */
    @Test(timeout = 4000)
    public void readingFromNegativePositionReturnsMinusOne() throws Throwable {
        // Build one 6-byte backing source and a couple of wrappers around it; all share the
        // same length, so the concrete wrapper type used for each slot does not matter here.
        byte[] backingBytes = new byte[BYTES_PER_SOURCE];
        ArrayRandomAccessSource arraySource = new ArrayRandomAccessSource(backingBytes);
        GetBufferedRandomAccessSource bufferedSource = new GetBufferedRandomAccessSource(arraySource);
        IndependentRandomAccessSource independentSource = new IndependentRandomAccessSource(bufferedSource);

        // Eight 6-byte sources arranged back to back.
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

        long negativePosition = -1L;
        int bytesRead = groupedSource.get(negativePosition, backingBytes, 1310, -156);

        assertEquals("Total length is the sum of all member source lengths",
                (long) (SOURCE_COUNT * BYTES_PER_SOURCE), groupedSource.length());
        assertEquals("A negative read position yields no bytes (-1)", -1, bytesRead);
    }
}
