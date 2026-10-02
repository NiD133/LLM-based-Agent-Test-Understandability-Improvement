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

    /** Number of bytes backing each underlying source. */
    private static final int BYTES_PER_SOURCE = 8;

    /** Number of underlying sources grouped together. */
    private static final int SOURCE_COUNT = 8;

    /**
     * A GroupedRandomAccessSource spans the concatenation of its underlying sources,
     * so its length is the sum of their lengths. A get() call whose length argument is
     * negative reads nothing and returns -1.
     */
    @Test(timeout = 4000)
    public void getWithNegativeLengthReturnsMinusOneAndLengthIsSumOfSources() throws Throwable {
        // Build eight sources, all backed by the same 8-byte buffer, and group them.
        byte[] buffer = new byte[BYTES_PER_SOURCE];
        ArrayRandomAccessSource arraySource = new ArrayRandomAccessSource(buffer);
        GetBufferedRandomAccessSource bufferedSource = new GetBufferedRandomAccessSource(arraySource);
        IndependentRandomAccessSource independentSource = new IndependentRandomAccessSource(bufferedSource);

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

        // A negative read length means no bytes are read, so get() returns -1.
        int bytesRead = groupedSource.get(0L, buffer, -126, -43);
        assertEquals(-1, bytesRead);

        // Length spans all eight 8-byte sources: 8 * 8 == 64.
        assertEquals(64L, groupedSource.length());
    }
}
