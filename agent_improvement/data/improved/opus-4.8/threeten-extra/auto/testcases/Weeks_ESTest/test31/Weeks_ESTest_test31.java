package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Weeks_ESTest_test31 extends Weeks_ESTest_scaffolding {

    /**
     * Verifies that {@link Weeks#toString()} renders a one-week amount
     * using the ISO-8601 period format "PnW", i.e. "P1W" for one week.
     */
    @Test(timeout = 4000)
    public void toString_oneWeek_returnsIso8601PeriodFormat() throws Throwable {
        Weeks oneWeek = Weeks.ONE;

        String formatted = oneWeek.toString();

        assertEquals("P1W", formatted);
    }
}
