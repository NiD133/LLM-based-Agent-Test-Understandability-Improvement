package org.threeten.extra;

import static org.junit.Assert.assertNotNull;

import java.time.Period;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Years_ESTest_test29 extends Years_ESTest_scaffolding {

    /**
     * Converting the constant {@link Years#ONE} to a {@link Period}
     * should yield a non-null period.
     */
    @Test(timeout = 4000)
    public void toPeriod_fromOneYear_returnsNonNullPeriod() throws Throwable {
        Period period = Years.ONE.toPeriod();

        assertNotNull(period);
    }
}
