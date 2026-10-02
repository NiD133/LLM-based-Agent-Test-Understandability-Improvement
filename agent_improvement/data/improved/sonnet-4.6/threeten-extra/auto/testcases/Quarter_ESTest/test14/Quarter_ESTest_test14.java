package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.temporal.ChronoField;
import java.time.temporal.UnsupportedTemporalTypeException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Quarter_ESTest_test14 extends Quarter_ESTest_scaffolding {

    /**
     * Quarter.range() must throw UnsupportedTemporalTypeException for any ChronoField
     * other than QUARTER_OF_YEAR, because Quarter only supports quarter-level fields.
     * AMPM_OF_DAY is a time-of-day ChronoField that has no meaning in a quarter context.
     */
    @Test(timeout = 4000)
    public void test14_range_throwsForUnsupportedChronoField() throws Throwable {
        Quarter firstQuarter = Quarter.Q1;
        ChronoField amPmOfDay = ChronoField.AMPM_OF_DAY;

        try {
            firstQuarter.range(amPmOfDay);
            fail("Expecting exception: UnsupportedTemporalTypeException");
        } catch (UnsupportedTemporalTypeException e) {
            verifyException("org.threeten.extra.Quarter", e);
        }
    }
}
