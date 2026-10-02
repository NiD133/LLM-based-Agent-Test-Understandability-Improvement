package org.apache.commons.io.input;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class UnsynchronizedByteArrayInputStream_ESTest_test02 extends UnsynchronizedByteArrayInputStream_ESTest_scaffolding {

    private static final int BUFFER_LENGTH = 6;
    private static final int CONSTRUCTOR_OFFSET = 304;
    private static final long NEGATIVE_SKIP_DISTANCE = -1634L;

    @Test(timeout = 4000)
    public void test02() throws Throwable {
        byte[] buffer = new byte[BUFFER_LENGTH];
        UnsynchronizedByteArrayInputStream inputStream = new UnsynchronizedByteArrayInputStream(buffer, CONSTRUCTOR_OFFSET);

        try {
            inputStream.skip(NEGATIVE_SKIP_DISTANCE);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException exception) {
            verifyException("org.apache.commons.io.input.UnsynchronizedByteArrayInputStream", exception);
        }
    }
}
