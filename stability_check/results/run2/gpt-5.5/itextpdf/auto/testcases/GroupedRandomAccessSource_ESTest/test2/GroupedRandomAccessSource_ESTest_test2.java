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

    @Test(timeout = 4000)
    public void test2() throws Throwable {
        RandomAccessSource[] sources = new RandomAccessSource[8];
        byte[] backingBytes = new byte[8];

        ArrayRandomAccessSource arraySource = new ArrayRandomAccessSource(backingBytes);
        GetBufferedRandomAccessSource bufferedSource = new GetBufferedRandomAccessSource(arraySource);

        sources[0] = (RandomAccessSource) bufferedSource;
        sources[1] = (RandomAccessSource) arraySource;
        sources[2] = (RandomAccessSource) bufferedSource;
        sources[3] = (RandomAccessSource) bufferedSource;
        IndependentRandomAccessSource independentSource = new IndependentRandomAccessSource(bufferedSource);
        sources[4] = (RandomAccessSource) independentSource;
        sources[5] = (RandomAccessSource) bufferedSource;
        sources[6] = (RandomAccessSource) arraySource;
        sources[7] = (RandomAccessSource) independentSource;

        GroupedRandomAccessSource groupedSource = new GroupedRandomAccessSource(sources);
        int bytesRead = groupedSource.get((long) (byte) 0, backingBytes, (int) (byte) (-126), (int) (byte) (-43));

        assertEquals((-1), bytesRead);
        assertEquals(64L, groupedSource.length());
    }
}
