package com.itextpdf.text.io;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class GroupedRandomAccessSource_ESTest_test1 extends GroupedRandomAccessSource_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test1() throws Throwable {
        byte[] sharedBuffer = new byte[6];
        ArrayRandomAccessSource backingSource = new ArrayRandomAccessSource(sharedBuffer);

        RandomAccessSource[] groupedSources = new RandomAccessSource[1];
        WindowRandomAccessSource windowSource = new WindowRandomAccessSource(backingSource, 807L, 807L);
        groupedSources[0] = (RandomAccessSource) windowSource;

        GroupedRandomAccessSource groupedSource = new GroupedRandomAccessSource(groupedSources);

        int bytesRead = groupedSource.get(196L, sharedBuffer, 1318, 1318);

        assertEquals((-1), bytesRead);
        assertEquals(807L, groupedSource.length());
    }
}
