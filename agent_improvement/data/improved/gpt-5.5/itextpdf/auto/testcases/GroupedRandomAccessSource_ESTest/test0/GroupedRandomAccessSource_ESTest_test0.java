package com.itextpdf.text.io;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class GroupedRandomAccessSource_ESTest_test0 extends GroupedRandomAccessSource_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test0() throws Throwable {
        RandomAccessSource[] groupedSources = new RandomAccessSource[8];
        byte[] sixByteContent = new byte[6];
        ArrayRandomAccessSource arraySource = new ArrayRandomAccessSource(sixByteContent);
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
        groupedSource.close();

        // Eight six-byte sources are still reported as a grouped length of 48 after close().
        assertEquals(48L, groupedSource.length());
    }
}
