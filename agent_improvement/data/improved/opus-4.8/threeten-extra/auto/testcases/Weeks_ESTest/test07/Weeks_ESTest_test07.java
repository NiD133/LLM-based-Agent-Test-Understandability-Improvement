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

    /**
     * Subtracting one week from a one-week amount yields a zero-week amount.
     * Adding that zero amount to a date is a valid no-op, and the amount
     * still reports itself as zero.
     */
    @Test(timeout = 4000)
    public void subtractingOneWeekFromOneWeekGivesZeroWeeks() throws Throwable {
        Weeks oneWeek = Weeks.of(1);

        Weeks zeroWeeks = oneWeek.minus(1);

        LocalDate today = MockLocalDate.now();
        zeroWeeks.addTo(today);

        assertTrue(zeroWeeks.isZero());
    }
}
