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
public class Quarter_ESTest_test13 extends Quarter_ESTest_scaffolding {

    /**
     * Quarter.get(TemporalField) only supports QUARTER_OF_YEAR. Querying it with
     * any ChronoField (here CLOCK_HOUR_OF_AMPM) must raise an
     * UnsupportedTemporalTypeException thrown by Quarter itself.
     */
    @Test(timeout = 4000)
    public void get_withUnsupportedChronoField_throwsUnsupportedTemporalType() throws Throwable {
        Quarter quarter = Quarter.Q2;
        ChronoField unsupportedField = ChronoField.CLOCK_HOUR_OF_AMPM;

        try {
            quarter.get(unsupportedField);
            fail("Expecting exception: UnsupportedTemporalTypeException");
        } catch (UnsupportedTemporalTypeException e) {
            // Message: "Unsupported field: ClockHourOfAmPm"
            verifyException("org.threeten.extra.Quarter", e);
        }
    }
}
