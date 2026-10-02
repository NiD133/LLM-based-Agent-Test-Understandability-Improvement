package com.itextpdf.text.io;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class GroupedRandomAccessSource_ESTest_test8 extends GroupedRandomAccessSource_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test8() throws Throwable {
        byte[] destinationAndSourceBuffer = new byte[6];
        ArrayRandomAccessSource sharedArraySource = new ArrayRandomAccessSource(destinationAndSourceBuffer);

        RandomAccessSource[] groupedSources = new RandomAccessSource[2];
        groupedSources[0] = (RandomAccessSource) sharedArraySource;
        groupedSources[1] = (RandomAccessSource) sharedArraySource;

        GroupedRandomAccessSource groupedSource = new GroupedRandomAccessSource(groupedSources);

        try {
            groupedSource.get((long) 5, destinationAndSourceBuffer, 5, 5);
            fail("Expecting exception: ArrayIndexOutOfBoundsException");
        } catch (ArrayIndexOutOfBoundsException e) {
            verifyException("com.itextpdf.text.io.ArrayRandomAccessSource", e);
        }
    }
}
