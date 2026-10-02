package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.Period;
import java.time.temporal.TemporalAmount;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Weeks_ESTest_test27 extends Weeks_ESTest_scaffolding {

    // Subtracting a Period equal to one week from Weeks.ONE yields zero weeks,
    // and the original Weeks.ONE instance is unchanged (immutability).
    @Test(timeout = 4000)
    public void testMinusEquivalentPeriodYieldsZeroAndOriginalIsUnchanged() throws Throwable {
        Weeks oneWeek = Weeks.ONE;
        Period oneWeekAsPeriod = oneWeek.toPeriod();
        Weeks result = oneWeek.minus((TemporalAmount) oneWeekAsPeriod);
        assertEquals(0, result.getAmount());
        assertEquals(1, oneWeek.getAmount());
    }
}
