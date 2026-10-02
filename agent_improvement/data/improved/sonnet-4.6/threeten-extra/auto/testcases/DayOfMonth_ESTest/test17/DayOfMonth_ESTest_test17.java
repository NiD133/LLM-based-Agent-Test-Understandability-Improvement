package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DayOfMonth_ESTest_test17 extends DayOfMonth_ESTest_scaffolding {

    // DayOfMonth.from() short-circuits when given a DayOfMonth, returning it directly.
    // The mock clock fixes "now" to day 14, so both now() and from(now()) yield 14.
    @Test(timeout = 4000)
    public void test_fromDayOfMonth_returnsSameValue() throws Throwable {
        DayOfMonth today = DayOfMonth.now();
        DayOfMonth fromToday = DayOfMonth.from(today);
        assertEquals(14, fromToday.getValue());
    }
}
