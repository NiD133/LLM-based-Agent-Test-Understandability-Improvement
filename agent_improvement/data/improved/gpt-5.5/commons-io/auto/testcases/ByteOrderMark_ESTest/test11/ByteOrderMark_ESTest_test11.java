package org.apache.commons.io;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ByteOrderMark_ESTest_test11 extends ByteOrderMark_ESTest_scaffolding {

    private static final String GENERATED_CHARSET_NAME = "(\"09%p_HU|M5jy";

    @Test(timeout = 4000)
    public void test11() throws Throwable {
        int[] emptyByteSequence = new int[0];
        ByteOrderMark byteOrderMark = null;

        try {
            byteOrderMark = new ByteOrderMark(GENERATED_CHARSET_NAME, emptyByteSequence);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //
            // No bytes specified
            //
            verifyException("org.apache.commons.io.ByteOrderMark", e);
        }
    }
}
