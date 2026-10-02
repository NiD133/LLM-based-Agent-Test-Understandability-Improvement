package org.threeten.extra;

import static org.junit.Assert.assertTrue;

import java.time.chrono.HijrahDate;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.chrono.MockHijrahDate;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Weeks_ESTest_test30 extends Weeks_ESTest_scaffolding {

    /**
     * The number of weeks between a date and itself should be zero,
     * since the span between identical start and end dates is empty.
     */
    @Test(timeout = 4000)
    public void betweenSameDateReturnsZeroWeeks() throws Throwable {
        HijrahDate sameDate = MockHijrahDate.now();

        Weeks weeksBetween = Weeks.between(sameDate, sameDate);

        assertTrue("Weeks between a date and itself must be zero", weeksBetween.isZero());
    }
}
