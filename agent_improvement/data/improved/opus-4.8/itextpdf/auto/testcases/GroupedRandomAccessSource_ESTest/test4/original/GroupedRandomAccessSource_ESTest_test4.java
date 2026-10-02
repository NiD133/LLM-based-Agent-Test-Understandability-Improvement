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
        RandomAccessSource[] randomAccessSourceArray0 = new RandomAccessSource[8];
        byte[] byteArray0 = new byte[7];
        ArrayRandomAccessSource arrayRandomAccessSource0 = new ArrayRandomAccessSource(byteArray0);
        GetBufferedRandomAccessSource getBufferedRandomAccessSource0 = new GetBufferedRandomAccessSource(arrayRandomAccessSource0);
        randomAccessSourceArray0[0] = (RandomAccessSource) getBufferedRandomAccessSource0;
        randomAccessSourceArray0[1] = (RandomAccessSource) arrayRandomAccessSource0;
        randomAccessSourceArray0[2] = (RandomAccessSource) getBufferedRandomAccessSource0;
        randomAccessSourceArray0[3] = (RandomAccessSource) getBufferedRandomAccessSource0;
        IndependentRandomAccessSource independentRandomAccessSource0 = new IndependentRandomAccessSource(getBufferedRandomAccessSource0);
        randomAccessSourceArray0[4] = (RandomAccessSource) independentRandomAccessSource0;
        randomAccessSourceArray0[5] = (RandomAccessSource) getBufferedRandomAccessSource0;
        randomAccessSourceArray0[6] = (RandomAccessSource) arrayRandomAccessSource0;
        randomAccessSourceArray0[7] = (RandomAccessSource) independentRandomAccessSource0;
        GroupedRandomAccessSource groupedRandomAccessSource0 = new GroupedRandomAccessSource(randomAccessSourceArray0);
        int int0 = groupedRandomAccessSource0.get(0L);
        assertEquals(56L, groupedRandomAccessSource0.length());
        assertEquals(0, int0);
    }
}
