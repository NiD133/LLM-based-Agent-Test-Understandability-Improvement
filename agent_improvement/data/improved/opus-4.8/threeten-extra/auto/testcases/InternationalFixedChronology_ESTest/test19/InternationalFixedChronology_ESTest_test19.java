package org.threeten.extra.chrono;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class InternationalFixedChronology_ESTest_test19 extends InternationalFixedChronology_ESTest_scaffolding {

    /**
     * The current date (obtained from the mocked clock, which sits in a non-leap year)
     * should report a year length of 365 days in the International Fixed calendar.
     */
    @Test(timeout = 4000)
    public void dateNow_inNonLeapYear_hasYearLengthOf365Days() throws Throwable {
        InternationalFixedChronology chronology = new InternationalFixedChronology();

        InternationalFixedDate today = chronology.dateNow();

        assertEquals(365, today.lengthOfYear());
    }
}
