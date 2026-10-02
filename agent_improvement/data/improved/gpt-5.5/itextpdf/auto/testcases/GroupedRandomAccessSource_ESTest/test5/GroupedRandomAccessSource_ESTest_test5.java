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

    @Test(timeout = 4000)
    public void test5() throws Throwable {
        RandomAccessSource[] sources = new RandomAccessSource[8];
        byte[] backingBytesAndReadBuffer = new byte[6];

        ArrayRandomAccessSource arraySource = new ArrayRandomAccessSource(backingBytesAndReadBuffer);
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
        long readPosition = (long) (-1);
        int bufferOffset = 1310;
        int requestedLength = (-156);

        int bytesRead = groupedSource.get(readPosition, backingBytesAndReadBuffer, bufferOffset, requestedLength);

        assertEquals(48L, groupedSource.length());
        assertEquals((-1), bytesRead);
    }
}
