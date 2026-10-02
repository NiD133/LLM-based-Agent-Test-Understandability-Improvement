package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DayOfYear_ESTest_test09 extends DayOfYear_ESTest_scaffolding {

    /**
     * Under EvoSuite's mocked clock, {@link DayOfYear#now()} resolves to the 45th day-of-year.
     * Since that day is below 366, {@link DayOfYear#isValidYear(int)} must return true for any
     * year, even a negative (and non-leap) one such as -1717.
     */
    @Test(timeout = 4000)
    public void isValidYear_returnsTrue_forNonLastDayRegardlessOfYear() throws Throwable {
        DayOfYear currentDayOfYear = DayOfYear.now();

        boolean valid = currentDayOfYear.isValidYear(-1717);

        assertEquals(45, currentDayOfYear.getValue());
        assertTrue(valid);
    }
}
