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

    private static final int SOURCE_DATA_SIZE = 8;
    private static final int SOURCE_COUNT = 8;

    // Negative offset and length make the read call invalid; get() must return -1
    private static final int INVALID_OFFSET = (int) (byte) (-126); // -126
    private static final int INVALID_LENGTH = (int) (byte) (-43);  // -43

    private static final long READ_POSITION = (long) (byte) 0;     // 0L
    // 8 sources each covering 8 bytes → total length = 64
    private static final long EXPECTED_TOTAL_LENGTH = 64L;

    @Test(timeout = 4000)
    public void test2() throws Throwable {
        // A single 8-byte backing array shared across all three source types
        byte[] sourceData = new byte[SOURCE_DATA_SIZE];

        ArrayRandomAccessSource arraySource = new ArrayRandomAccessSource(sourceData);
        GetBufferedRandomAccessSource bufferedSource = new GetBufferedRandomAccessSource(arraySource);
        IndependentRandomAccessSource independentSource = new IndependentRandomAccessSource(bufferedSource);

        // Populate the group with 8 slots (some sources reused), each covering 8 bytes
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

        // A read with a negative offset and negative length is invalid; expect -1
        int bytesRead = groupedSource.get(READ_POSITION, sourceData, INVALID_OFFSET, INVALID_LENGTH);
        assertEquals(-1, bytesRead);

        // Total length = 8 sources × 8 bytes each
        assertEquals(EXPECTED_TOTAL_LENGTH, groupedSource.length());
    }
}
