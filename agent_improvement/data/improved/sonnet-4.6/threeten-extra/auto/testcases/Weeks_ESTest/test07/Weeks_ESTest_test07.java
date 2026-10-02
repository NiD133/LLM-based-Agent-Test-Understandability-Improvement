package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.LocalDate;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockLocalDate;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Weeks_ESTest_test07 extends Weeks_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void subtractingOneFromOneWeekYieldsZeroAndAddToDateSucceeds() throws Throwable {
        Weeks oneWeek = Weeks.of(1);
        LocalDate today = MockLocalDate.now();

        // Subtracting 1 from 1 week produces zero weeks
        Weeks zeroWeeks = oneWeek.minus(1);

        // Adding zero weeks to a date is a no-op and must not throw
        zeroWeeks.addTo(today);

        assertTrue(zeroWeeks.isZero());
    }
}
