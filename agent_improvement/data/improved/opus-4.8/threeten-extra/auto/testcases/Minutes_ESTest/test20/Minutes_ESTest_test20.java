package org.threeten.extra;

import static org.junit.Assert.fail;
import static org.evosuite.runtime.EvoAssertions.verifyException;

import java.time.temporal.ChronoUnit;
import java.time.temporal.UnsupportedTemporalTypeException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Minutes_ESTest_test20 extends Minutes_ESTest_scaffolding {

    /**
     * {@code get(TemporalUnit)} only supports the MINUTES unit; requesting any
     * other unit (here MONTHS) must throw an UnsupportedTemporalTypeException.
     */
    @Test(timeout = 4000)
    public void get_withUnsupportedUnit_throwsUnsupportedTemporalTypeException() throws Throwable {
        Minutes zeroMinutes = Minutes.ZERO;
        ChronoUnit unsupportedUnit = ChronoUnit.MONTHS;

        try {
            zeroMinutes.get(unsupportedUnit);
            fail("Expecting exception: UnsupportedTemporalTypeException");
        } catch (UnsupportedTemporalTypeException e) {
            // Message: "Unsupported unit: Months"
            verifyException("org.threeten.extra.Minutes", e);
        }
    }
}
