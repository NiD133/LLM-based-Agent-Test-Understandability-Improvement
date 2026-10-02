package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.Period;
import java.time.temporal.TemporalAmount;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Weeks_ESTest_test27 extends Weeks_ESTest_scaffolding {

    /**
     * Subtracting "one week" (as a Period) from Weeks.ONE should yield zero weeks,
     * while leaving the original Weeks.ONE instance unchanged (immutability).
     */
    @Test(timeout = 4000)
    public void subtractingOneWeekPeriodFromOneWeekGivesZero() throws Throwable {
        Weeks oneWeek = Weeks.ONE;
        Period oneWeekAsPeriod = oneWeek.toPeriod();

        Weeks result = oneWeek.minus((TemporalAmount) oneWeekAsPeriod);

        assertEquals("1 week minus 1 week should be 0 weeks", 0, result.getAmount());
        assertEquals("original Weeks.ONE must remain unchanged", 1, oneWeek.getAmount());
    }
}
