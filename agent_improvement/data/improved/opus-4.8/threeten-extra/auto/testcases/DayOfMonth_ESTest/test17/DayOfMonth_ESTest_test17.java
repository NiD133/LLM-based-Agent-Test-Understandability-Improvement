package org.threeten.extra;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DayOfMonth_ESTest_test17 extends DayOfMonth_ESTest_scaffolding {

    /**
     * {@link DayOfMonth#from(java.time.temporal.TemporalAccessor)} should return a
     * {@code DayOfMonth} with the same value when given another {@code DayOfMonth}.
     * Under the mocked clock the current day-of-month is the 14th.
     */
    @Test(timeout = 4000)
    public void fromDayOfMonthPreservesValue() throws Throwable {
        DayOfMonth currentDay = DayOfMonth.now();

        DayOfMonth dayFromTemporal = DayOfMonth.from(currentDay);

        assertEquals(14, dayFromTemporal.getValue());
    }
}
