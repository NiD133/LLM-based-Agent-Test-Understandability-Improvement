package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class InternationalFixedChronology_ESTest_test21 extends InternationalFixedChronology_ESTest_scaffolding {

    /**
     * Verifies that a date built from proleptic-year 12, month 12, day 12 in the
     * International Fixed calendar converts to the expected epoch day (days
     * relative to 1970-01-01, hence negative for a year far in the past).
     */
    @Test(timeout = 4000)
    public void dateForYear12Month12Day12_hasExpectedEpochDay() throws Throwable {
        InternationalFixedChronology chronology = new InternationalFixedChronology();

        InternationalFixedDate date = chronology.date(12, 12, 12);

        long expectedEpochDay = -714825L;
        assertEquals(expectedEpochDay, date.toEpochDay());
    }
}
