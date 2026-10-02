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
        byte[] byteArray0 = new byte[6];
        ArrayRandomAccessSource arrayRandomAccessSource0 = new ArrayRandomAccessSource(byteArray0);
        RandomAccessSource[] randomAccessSourceArray0 = new RandomAccessSource[1];
        WindowRandomAccessSource windowRandomAccessSource0 = new WindowRandomAccessSource(arrayRandomAccessSource0, 807L, 807L);
        randomAccessSourceArray0[0] = (RandomAccessSource) windowRandomAccessSource0;
        GroupedRandomAccessSource groupedRandomAccessSource0 = new GroupedRandomAccessSource(randomAccessSourceArray0);
        int int0 = groupedRandomAccessSource0.get(196L, byteArray0, 1318, 1318);
        assertEquals((-1), int0);
        assertEquals(807L, groupedRandomAccessSource0.length());
    }
}
