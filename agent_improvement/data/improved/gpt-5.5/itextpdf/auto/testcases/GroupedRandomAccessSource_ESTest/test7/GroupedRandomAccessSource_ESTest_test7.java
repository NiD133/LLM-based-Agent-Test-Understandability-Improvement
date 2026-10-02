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
        RandomAccessSource[] sources = new RandomAccessSource[1];
        WindowRandomAccessSource windowWithNegativeLength =
                new WindowRandomAccessSource((RandomAccessSource) null, (-2147L), (-645L));
        sources[0] = (RandomAccessSource) windowWithNegativeLength;

        GroupedRandomAccessSource groupedSource = new GroupedRandomAccessSource(sources);

        long groupedLength = groupedSource.length();
        assertEquals((-645L), groupedLength);
    }
}
