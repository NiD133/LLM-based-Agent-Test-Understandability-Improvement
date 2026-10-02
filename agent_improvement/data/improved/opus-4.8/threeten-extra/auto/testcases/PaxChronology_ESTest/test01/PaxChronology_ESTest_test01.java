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
     * Verifies that the Pax chronology returns a valid (non-null) value range
     * for the MONTH_OF_YEAR field.
     */
    @Test(timeout = 4000)
    public void range_forMonthOfYear_returnsNonNullRange() throws Throwable {
        PaxDate paxDate = PaxDate.ofEpochDay(146096L);
        PaxChronology paxChronology = paxDate.getChronology();

        ValueRange monthOfYearRange = paxChronology.range(ChronoField.MONTH_OF_YEAR);

        assertNotNull(monthOfYearRange);
    }
}
