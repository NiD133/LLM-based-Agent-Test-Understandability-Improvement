package com.itextpdf.text.io;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class GroupedRandomAccessSource_ESTest_test6 extends GroupedRandomAccessSource_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test6() throws Throwable {
        byte[] backingBytes = new byte[6];
        ArrayRandomAccessSource arraySource = new ArrayRandomAccessSource(backingBytes);
        RandomAccessSource[] sources = new RandomAccessSource[] {
                (RandomAccessSource) arraySource
        };
        GroupedRandomAccessSource groupedSource = new GroupedRandomAccessSource(sources);

        int bytesRead = groupedSource.get((long) 4, backingBytes, 4, 4);

        assertEquals(6L, groupedSource.length());
        assertEquals(2, bytesRead);
    }
}
