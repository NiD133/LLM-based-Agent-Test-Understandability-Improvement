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
        byte[] byteArray0 = new byte[6];
        ArrayRandomAccessSource arrayRandomAccessSource0 = new ArrayRandomAccessSource(byteArray0);
        RandomAccessSource[] randomAccessSourceArray0 = new RandomAccessSource[2];
        randomAccessSourceArray0[0] = (RandomAccessSource) arrayRandomAccessSource0;
        randomAccessSourceArray0[1] = (RandomAccessSource) arrayRandomAccessSource0;
        GroupedRandomAccessSource groupedRandomAccessSource0 = new GroupedRandomAccessSource(randomAccessSourceArray0);
        // Undeclared exception!
        try {
            groupedRandomAccessSource0.get((long) 5, byteArray0, 5, 5);
            fail("Expecting exception: ArrayIndexOutOfBoundsException");
        } catch (ArrayIndexOutOfBoundsException e) {
            //
            // no message in exception (getMessage() returned null)
            //
            verifyException("com.itextpdf.text.io.ArrayRandomAccessSource", e);
        }
    }
}
