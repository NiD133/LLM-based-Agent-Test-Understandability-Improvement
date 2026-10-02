package org.threeten.extra;

import static org.junit.Assert.fail;
import static org.evosuite.runtime.EvoAssertions.verifyException;

import java.time.temporal.ChronoField;
import java.time.temporal.UnsupportedTemporalTypeException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Quarter_ESTest_test14 extends Quarter_ESTest_scaffolding {

    /**
     * Quarter only supports the QUARTER_OF_YEAR field. Querying the range of any
     * other ChronoField (here AMPM_OF_DAY) must fail with an
     * UnsupportedTemporalTypeException thrown by Quarter itself.
     */
    @Test(timeout = 4000)
    public void range_withUnsupportedChronoField_throwsUnsupportedTemporalTypeException() throws Throwable {
        Quarter quarter = Quarter.Q1;
        ChronoField unsupportedField = ChronoField.AMPM_OF_DAY;

        try {
            quarter.range(unsupportedField);
            fail("Expecting exception: UnsupportedTemporalTypeException");
        } catch (UnsupportedTemporalTypeException e) {
            // Message reads: "Unsupported field: AmPmOfDay"
            verifyException("org.threeten.extra.Quarter", e);
        }
    }
}
