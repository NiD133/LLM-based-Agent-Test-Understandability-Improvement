package org.apache.commons.compress.archivers.cpio;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CpioUtil_ESTest_test6 extends CpioUtil_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test6() throws Throwable {
        byte[] byteArray0 = new byte[1];
        // Undeclared exception!
        try {
            CpioUtil.byteArray2long(byteArray0, true);
            fail("Expecting exception: UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
            //
            // no message in exception (getMessage() returned null)
            //
            verifyException("org.apache.commons.compress.archivers.cpio.CpioUtil", e);
        }
    }
}
