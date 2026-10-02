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
    private static final int READ_OFFSET = (int) (byte) (-126);
    private static final int READ_LENGTH = (int) (byte) (-43);
    private static final long READ_POSITION = (long) (byte) 0;

    @Test(timeout = 4000)
    public void test2() throws Throwable {
        RandomAccessSource[] groupedSources = new RandomAccessSource[SOURCE_COUNT];
        byte[] sharedBuffer = new byte[SOURCE_COUNT];

        ArrayRandomAccessSource arraySource = new ArrayRandomAccessSource(sharedBuffer);
        GetBufferedRandomAccessSource bufferedSource = new GetBufferedRandomAccessSource(arraySource);
        IndependentRandomAccessSource independentSource = new IndependentRandomAccessSource(bufferedSource);

        groupedSources[0] = (RandomAccessSource) bufferedSource;
        groupedSources[1] = (RandomAccessSource) arraySource;
        groupedSources[2] = (RandomAccessSource) bufferedSource;
        groupedSources[3] = (RandomAccessSource) bufferedSource;
        groupedSources[4] = (RandomAccessSource) independentSource;
        groupedSources[5] = (RandomAccessSource) bufferedSource;
        groupedSources[6] = (RandomAccessSource) arraySource;
        groupedSources[7] = (RandomAccessSource) independentSource;

        GroupedRandomAccessSource groupedSource = new GroupedRandomAccessSource(groupedSources);

        int bytesRead = groupedSource.get(READ_POSITION, sharedBuffer, READ_OFFSET, READ_LENGTH);

        assertEquals((-1), bytesRead);
        assertEquals(64L, groupedSource.length());
    }
}
