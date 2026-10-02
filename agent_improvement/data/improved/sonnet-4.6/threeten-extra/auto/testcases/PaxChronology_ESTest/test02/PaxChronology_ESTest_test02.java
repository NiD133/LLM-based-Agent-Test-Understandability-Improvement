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
public class PaxChronology_ESTest_test02 extends PaxChronology_ESTest_scaffolding {

    /**
     * Verifies that PaxChronology.range(DAY_OF_MONTH) returns a non-null ValueRange.
     * The chronology is retrieved from a PaxDate constructed via epoch day,
     * confirming the range query is supported for this Pax-specific field override.
     */
    @Test(timeout = 4000)
    public void test_rangeForDayOfMonthReturnsNonNull() throws Throwable {
        PaxDate paxDate = PaxDate.ofEpochDay(146096L);
        PaxChronology chronology = paxDate.getChronology();

        ValueRange dayOfMonthRange = chronology.range(ChronoField.DAY_OF_MONTH);

        assertNotNull(dayOfMonthRange);
    }
}
