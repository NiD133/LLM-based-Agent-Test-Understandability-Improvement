package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.temporal.TemporalAmount;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Hours_ESTest_test31 extends Hours_ESTest_scaffolding {

    /**
     * Subtracting zero hours from zero hours yields zero hours, and since
     * {@link Hours#ZERO} is a singleton the result is the very same instance.
     */
    @Test(timeout = 4000)
    public void subtractingZeroHoursReturnsSameZeroInstance() throws Throwable {
        Hours zero = Hours.ZERO;

        Hours result = zero.minus((TemporalAmount) zero);

        assertSame(zero, result);
    }
}
