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
public class PaxChronology_ESTest_test02 extends PaxChronology_ESTest_scaffolding {

    /**
     * Verifies that the Pax chronology can supply the valid range of values
     * for the DAY_OF_MONTH field.
     */
    @Test(timeout = 4000)
    public void rangeForDayOfMonthReturnsValueRange() throws Throwable {
        // Obtain the PaxChronology instance via an arbitrary PaxDate.
        PaxDate paxDate = PaxDate.ofEpochDay(146096L);
        PaxChronology paxChronology = paxDate.getChronology();

        // Query the supported range for the day-of-month field.
        ValueRange dayOfMonthRange = paxChronology.range(ChronoField.DAY_OF_MONTH);

        assertNotNull(dayOfMonthRange);
    }
}
