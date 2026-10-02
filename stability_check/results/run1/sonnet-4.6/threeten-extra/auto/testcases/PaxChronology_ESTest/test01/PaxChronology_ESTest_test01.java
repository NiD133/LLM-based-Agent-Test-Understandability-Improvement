package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.temporal.ChronoField;
import java.time.temporal.ValueRange;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class PaxChronology_ESTest_test01 extends PaxChronology_ESTest_scaffolding {

    // Verifies that PaxChronology.range() returns a non-null ValueRange for MONTH_OF_YEAR,
    // which accounts for 13 months in a standard year and 14 in a leap year.
    @Test(timeout = 4000)
    public void test_range_returnsNonNull_forMonthOfYearField() throws Throwable {
        PaxDate date = PaxDate.ofEpochDay(146096L);
        PaxChronology chronology = date.getChronology();
        ValueRange monthOfYearRange = chronology.range(ChronoField.MONTH_OF_YEAR);
        assertNotNull(monthOfYearRange);
    }
}
