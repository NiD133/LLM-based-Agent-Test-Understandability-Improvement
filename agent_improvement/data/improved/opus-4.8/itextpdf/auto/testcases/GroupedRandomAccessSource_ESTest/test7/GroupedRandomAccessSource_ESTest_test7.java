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

    /**
     * A GroupedRandomAccessSource reports a total length equal to the sum of the
     * lengths of the sources it wraps. With a single wrapped source, the reported
     * length should match that source's length exactly.
     */
    @Test(timeout = 4000)
    public void length_withSingleWrappedSource_equalsThatSourceLength() throws Throwable {
        long windowOffset = -2147L;
        long windowLength = -645L;
        WindowRandomAccessSource windowSource =
                new WindowRandomAccessSource((RandomAccessSource) null, windowOffset, windowLength);

        RandomAccessSource[] sources = new RandomAccessSource[] { windowSource };
        GroupedRandomAccessSource groupedSource = new GroupedRandomAccessSource(sources);

        long totalLength = groupedSource.length();

        assertEquals(windowLength, totalLength);
    }
}
