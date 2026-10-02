package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.Duration;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Hours_ESTest_test10 extends Hours_ESTest_scaffolding {

    /**
     * Verifies that dividing a zero-hour amount by 1 yields zero hours.
     * Duration.ZERO converts to Hours.ZERO, and integer division by 1 is a no-op.
     */
    @Test(timeout = 4000)
    public void test_dividedByOne_onZeroHours_returnsZero() throws Throwable {
        Hours zeroHours = Hours.from(Duration.ZERO);
        Hours result = zeroHours.dividedBy(1);
        assertEquals(0, result.getAmount());
    }
}
