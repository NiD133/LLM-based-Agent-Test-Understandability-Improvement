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
        byte[] byteArray0 = new byte[6];
        ArrayRandomAccessSource arrayRandomAccessSource0 = new ArrayRandomAccessSource(byteArray0);
        RandomAccessSource[] randomAccessSourceArray0 = new RandomAccessSource[1];
        randomAccessSourceArray0[0] = (RandomAccessSource) arrayRandomAccessSource0;
        GroupedRandomAccessSource groupedRandomAccessSource0 = new GroupedRandomAccessSource(randomAccessSourceArray0);
        int int0 = groupedRandomAccessSource0.get((long) 4, byteArray0, 4, 4);
        assertEquals(6L, groupedRandomAccessSource0.length());
        assertEquals(2, int0);
    }
}
