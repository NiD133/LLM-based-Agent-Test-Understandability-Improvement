package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.temporal.ChronoField;
import java.time.temporal.ValueRange;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class PaxChronology_ESTest_test01 extends PaxChronology_ESTest_scaffolding {

    /**
     * Verifies that PaxChronology.range(MONTH_OF_YEAR) returns a non-null ValueRange.
     * The Pax calendar has 13 months in a standard year and 14 in a leap year,
     * so a valid range must exist for this field.
     */
    @Test(timeout = 4000)
    public void rangeForMonthOfYearShouldNotBeNull() throws Throwable {
        // Create a PaxDate from a known epoch day and retrieve its chronology
        PaxDate paxDate = PaxDate.ofEpochDay(146096L);
        PaxChronology paxChronology = paxDate.getChronology();

        // Query the valid range for the MONTH_OF_YEAR field in the Pax calendar
        ValueRange monthOfYearRange = paxChronology.range(ChronoField.MONTH_OF_YEAR);

        assertNotNull(monthOfYearRange);
    }
}
