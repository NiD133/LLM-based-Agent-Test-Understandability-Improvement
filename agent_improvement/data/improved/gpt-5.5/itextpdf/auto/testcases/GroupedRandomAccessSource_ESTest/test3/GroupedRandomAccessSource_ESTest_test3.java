package com.itextpdf.text.io;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class GroupedRandomAccessSource_ESTest_test3 extends GroupedRandomAccessSource_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test3() throws Throwable {
        RandomAccessSource[] groupedSources = new RandomAccessSource[8];
        byte[] backingBytes = new byte[8];
        ArrayRandomAccessSource arraySource = new ArrayRandomAccessSource(backingBytes);
        GetBufferedRandomAccessSource bufferedSource = new GetBufferedRandomAccessSource(arraySource);

        groupedSources[0] = (RandomAccessSource) bufferedSource;
        groupedSources[1] = (RandomAccessSource) arraySource;
        groupedSources[2] = (RandomAccessSource) bufferedSource;
        groupedSources[3] = (RandomAccessSource) bufferedSource;
        IndependentRandomAccessSource independentSource = new IndependentRandomAccessSource(bufferedSource);
        groupedSources[4] = (RandomAccessSource) independentSource;
        groupedSources[5] = (RandomAccessSource) bufferedSource;
        groupedSources[6] = (RandomAccessSource) arraySource;
        groupedSources[7] = (RandomAccessSource) independentSource;

        GroupedRandomAccessSource groupedSource = new GroupedRandomAccessSource(groupedSources);

        int valueBeforeStart = groupedSource.get((-1L));

        assertEquals(64L, groupedSource.length());
        assertEquals((-1), valueBeforeStart);
    }
}
