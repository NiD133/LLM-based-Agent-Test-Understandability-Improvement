package com.itextpdf.text.io;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class GroupedRandomAccessSource_ESTest_test7 extends GroupedRandomAccessSource_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test7() throws Throwable {
        RandomAccessSource[] randomAccessSourceArray0 = new RandomAccessSource[1];
        WindowRandomAccessSource windowRandomAccessSource0 = new WindowRandomAccessSource((RandomAccessSource) null, (-2147L), (-645L));
        randomAccessSourceArray0[0] = (RandomAccessSource) windowRandomAccessSource0;
        GroupedRandomAccessSource groupedRandomAccessSource0 = new GroupedRandomAccessSource(randomAccessSourceArray0);
        long long0 = groupedRandomAccessSource0.length();
        assertEquals((-645L), long0);
    }
}
