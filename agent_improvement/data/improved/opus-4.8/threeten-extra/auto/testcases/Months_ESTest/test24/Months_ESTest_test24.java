package org.threeten.extra;

import static org.junit.Assert.assertNotNull;

import java.time.Period;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Months_ESTest_test24 extends Months_ESTest_scaffolding {

    /**
     * Converting the singleton {@code Months.ONE} to a {@code Period}
     * should yield a non-null period.
     */
    @Test(timeout = 4000)
    public void toPeriodReturnsNonNullPeriod() throws Throwable {
        Months oneMonth = Months.ONE;

        Period period = oneMonth.toPeriod();

        assertNotNull(period);
    }
}
