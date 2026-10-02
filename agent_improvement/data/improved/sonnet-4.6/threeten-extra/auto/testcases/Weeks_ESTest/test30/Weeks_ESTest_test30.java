package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.chrono.HijrahDate;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.chrono.MockHijrahDate;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Weeks_ESTest_test30 extends Weeks_ESTest_scaffolding {

    /**
     * Weeks.between returns zero when start and end dates are the same Hijrah date.
     */
    @Test(timeout = 4000)
    public void test_weeksBetweenSameHijrahDate_isZero() throws Throwable {
        HijrahDate today = MockHijrahDate.now();

        Weeks weeksBetweenSameDate = Weeks.between(today, today);

        assertTrue(weeksBetweenSameDate.isZero());
    }
}
