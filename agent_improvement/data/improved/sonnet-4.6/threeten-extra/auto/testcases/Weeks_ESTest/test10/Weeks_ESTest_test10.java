package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Weeks_ESTest_test10 extends Weeks_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void dividedByZero_throwsArithmeticException() throws Throwable {
        Weeks zeroWeeks = Weeks.ZERO;
        try {
            zeroWeeks.dividedBy(0);
            fail("Expecting exception: ArithmeticException");
        } catch (ArithmeticException e) {
            verifyException("org.threeten.extra.Weeks", e);
        }
    }
}
