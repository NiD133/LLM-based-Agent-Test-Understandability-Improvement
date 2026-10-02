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

    @Test(timeout = 4000)
    public void test_fromPeriodOfOneWeek_returns168Hours() throws Throwable {
        // 1 week = 7 days × 24 hours/day = 168 hours
        Period oneWeek = Period.ofWeeks(1);
        Hours result = Hours.from(oneWeek);
        assertEquals(168, result.getAmount());
    }
}
