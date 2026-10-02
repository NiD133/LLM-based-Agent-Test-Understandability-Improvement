package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.Period;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Months_ESTest_test20 extends Months_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test20() throws Throwable {
        // minusMonths(-1L) on a zero-year period effectively adds 1 month
        Period zeroPeriod = Period.ofYears(0);
        Period oneMonthPeriod = zeroPeriod.minusMonths((-1L));
        Months oneMonth = Months.from(oneMonthPeriod);
        assertEquals(1, oneMonth.getAmount());
    }
}
