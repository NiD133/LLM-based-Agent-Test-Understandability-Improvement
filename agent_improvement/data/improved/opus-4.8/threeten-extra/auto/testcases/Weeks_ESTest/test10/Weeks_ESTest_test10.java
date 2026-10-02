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

    /**
     * Dividing a Weeks amount by zero must throw an ArithmeticException
     * ("/ by zero"), raised from within the Weeks class itself.
     */
    @Test(timeout = 4000)
    public void dividedByZeroThrowsArithmeticException() throws Throwable {
        Weeks zeroWeeks = Weeks.ZERO;

        try {
            zeroWeeks.dividedBy(0);
            fail("Expected an ArithmeticException for division by zero");
        } catch (ArithmeticException expected) {
            // The exception must originate from Weeks.dividedBy, not elsewhere.
            verifyException("org.threeten.extra.Weeks", expected);
        }
    }
}
