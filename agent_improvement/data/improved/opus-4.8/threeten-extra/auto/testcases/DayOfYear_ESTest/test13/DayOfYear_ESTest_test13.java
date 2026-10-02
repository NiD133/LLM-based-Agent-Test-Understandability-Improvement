package org.threeten.extra;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.temporal.ChronoField;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DayOfYear_ESTest_test13 extends DayOfYear_ESTest_scaffolding {

    /**
     * DAY_OF_YEAR is the one field a DayOfYear supports, so isSupported should
     * report true for it. The current day-of-year is fixed at 45 by the mocked clock.
     */
    @Test(timeout = 4000)
    public void isSupported_returnsTrue_forDayOfYearField() throws Throwable {
        DayOfYear currentDayOfYear = DayOfYear.now();

        boolean dayOfYearIsSupported = currentDayOfYear.isSupported(ChronoField.DAY_OF_YEAR);

        assertEquals(45, currentDayOfYear.getValue());
        assertTrue(dayOfYearIsSupported);
    }
}
