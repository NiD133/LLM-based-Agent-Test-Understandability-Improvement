package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.Period;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Months_ESTest_test24 extends Months_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_toPeriod_returnsNonNullPeriodForOneMonth() throws Throwable {
        // Months.ONE represents a single month; toPeriod() should produce an equivalent Period
        Months oneMonth = Months.ONE;
        Period period = oneMonth.toPeriod();
        assertNotNull(period);
    }
}
