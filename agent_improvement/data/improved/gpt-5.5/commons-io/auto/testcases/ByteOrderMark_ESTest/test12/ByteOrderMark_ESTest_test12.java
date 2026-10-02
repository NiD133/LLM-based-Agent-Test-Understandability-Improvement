package org.apache.commons.io;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ByteOrderMark_ESTest_test12 extends ByteOrderMark_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test12() throws Throwable {
        int[] singleByteMarker = new int[1];

        try {
            new ByteOrderMark("", singleByteMarker);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
            verifyException("org.apache.commons.io.ByteOrderMark", expected);
        }
    }
}
