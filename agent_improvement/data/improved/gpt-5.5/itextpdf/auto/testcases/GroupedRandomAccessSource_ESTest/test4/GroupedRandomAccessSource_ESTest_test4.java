package com.itextpdf.text.io;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class GroupedRandomAccessSource_ESTest_test4 extends GroupedRandomAccessSource_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test4() throws Throwable {
        RandomAccessSource[] sources = new RandomAccessSource[8];
        byte[] backingBytes = new byte[7];

        ArrayRandomAccessSource arraySource = new ArrayRandomAccessSource(backingBytes);
        GetBufferedRandomAccessSource bufferedSource = new GetBufferedRandomAccessSource(arraySource);
        IndependentRandomAccessSource independentSource = new IndependentRandomAccessSource(bufferedSource);

        sources[0] = (RandomAccessSource) bufferedSource;
        sources[1] = (RandomAccessSource) arraySource;
        sources[2] = (RandomAccessSource) bufferedSource;
        sources[3] = (RandomAccessSource) bufferedSource;
        sources[4] = (RandomAccessSource) independentSource;
        sources[5] = (RandomAccessSource) bufferedSource;
        sources[6] = (RandomAccessSource) arraySource;
        sources[7] = (RandomAccessSource) independentSource;

        GroupedRandomAccessSource groupedSource = new GroupedRandomAccessSource(sources);
        int firstByte = groupedSource.get(0L);

        assertEquals(56L, groupedSource.length());
        assertEquals(0, firstByte);
    }
}
