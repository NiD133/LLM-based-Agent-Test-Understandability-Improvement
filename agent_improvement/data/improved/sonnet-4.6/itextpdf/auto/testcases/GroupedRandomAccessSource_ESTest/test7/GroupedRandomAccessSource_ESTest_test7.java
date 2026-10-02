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

    // The length of the GroupedRandomAccessSource equals the sum of its underlying sources' lengths.
    // A WindowRandomAccessSource with a negative length contributes that negative value to the total.
    @Test(timeout = 4000)
    public void test_lengthReturnsNegativeWindowLength_whenWindowSourceHasNegativeLength() throws Throwable {
        long windowOffset = -2147L;
        long windowLength = -645L;

        WindowRandomAccessSource windowSource = new WindowRandomAccessSource((RandomAccessSource) null, windowOffset, windowLength);

        RandomAccessSource[] sources = new RandomAccessSource[] { windowSource };
        GroupedRandomAccessSource groupedSource = new GroupedRandomAccessSource(sources);

        long actualLength = groupedSource.length();

        assertEquals(windowLength, actualLength);
    }
}
