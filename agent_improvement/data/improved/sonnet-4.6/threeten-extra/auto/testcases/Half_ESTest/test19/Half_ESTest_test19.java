package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.DateTimeException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Half_ESTest_test19 extends Half_ESTest_scaffolding {

    /**
     * Half.of() only accepts 1 (H1) or 2 (H2). Passing 0 is out of range
     * and must throw DateTimeException with an appropriate message.
     */
    @Test(timeout = 4000)
    public void test19() throws Throwable {
        try {
            Half.of(0);
            fail("Expecting exception: DateTimeException");
        } catch (DateTimeException e) {
            verifyException("org.threeten.extra.Half", e);
        }
    }
}
