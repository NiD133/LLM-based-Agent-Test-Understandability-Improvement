package org.threeten.extra.chrono;

import static org.junit.Assert.fail;
import static org.evosuite.runtime.EvoAssertions.verifyException;

import java.time.DateTimeException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class InternationalFixedChronology_ESTest_test16 extends InternationalFixedChronology_ESTest_scaffolding {

    /**
     * Epoch day -719528 falls before the International Fixed calendar's supported range
     * (its proleptic year would be 0, below the minimum year-of-era of 1), so
     * {@code dateEpochDay} must reject it with a DateTimeException raised by ValueRange.
     */
    @Test(timeout = 4000)
    public void dateEpochDayBeforeSupportedRangeThrows() throws Throwable {
        InternationalFixedChronology chronology = new InternationalFixedChronology();

        long epochDayBeforeSupportedRange = -719528L;
        try {
            chronology.dateEpochDay(epochDayBeforeSupportedRange);
            fail("Expecting exception: DateTimeException");
        } catch (DateTimeException e) {
            // Invalid value for YearOfEra (valid values 1 - 1000000): -1
            verifyException("java.time.temporal.ValueRange", e);
        }
    }
}
