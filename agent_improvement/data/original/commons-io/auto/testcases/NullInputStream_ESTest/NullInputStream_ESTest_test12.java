package org.apache.commons.io.input;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.io.EOFException;
import java.io.IOException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class NullInputStream_ESTest_test12 extends NullInputStream_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test12() throws Throwable {
        NullInputStream nullInputStream0 = new NullInputStream((-4176L), false, true);
        byte[] byteArray0 = new byte[6];
        nullInputStream0.read(byteArray0, 0, (int) (byte) 1);
        try {
            nullInputStream0.skip((-2268L));
            fail("Expecting exception: EOFException");
        } catch (EOFException e) {
            //
            // handleEof()
            //
            verifyException("org.apache.commons.io.input.NullInputStream", e);
        }
    }
}
