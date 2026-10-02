package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.Period;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Hours_ESTest_test23 extends Hours_ESTest_scaffolding {

    /**
     * Verifies that converting a one-week {@link Period} to {@link Hours}
     * yields 168 hours (7 days * 24 hours per day).
     */
    @Test(timeout = 4000)
    public void from_oneWeekPeriod_returns168Hours() throws Throwable {
        Period oneWeek = Period.ofWeeks(1);

        Hours hours = Hours.from(oneWeek);

        assertEquals(7 * 24, hours.getAmount());
    }
}
