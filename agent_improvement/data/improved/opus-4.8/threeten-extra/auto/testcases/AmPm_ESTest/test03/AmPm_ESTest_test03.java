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
public class AmPm_ESTest_test03 extends AmPm_ESTest_scaffolding {

    /**
     * AmPm only supports the AMPM_OF_DAY field. Calling get() with any other
     * ChronoField (here MINUTE_OF_HOUR) must throw UnsupportedTemporalTypeException.
     */
    @Test(timeout = 4000)
    public void get_withUnsupportedField_throwsUnsupportedTemporalTypeException() throws Throwable {
        AmPm afternoon = AmPm.PM;
        ChronoField unsupportedField = ChronoField.MINUTE_OF_HOUR;

        try {
            afternoon.get(unsupportedField);
            fail("Expecting exception: UnsupportedTemporalTypeException");
        } catch (UnsupportedTemporalTypeException e) {
            // Unsupported field: MinuteOfHour
            verifyException("org.threeten.extra.AmPm", e);
        }
    }
}
