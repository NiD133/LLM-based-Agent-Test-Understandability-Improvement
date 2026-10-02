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
public class Days_ESTest_test21 extends Days_ESTest_scaffolding {

    /**
     * Days only supports the DAYS unit. Calling get(TemporalUnit) with any
     * other unit, such as HOURS, must throw UnsupportedTemporalTypeException.
     */
    @Test(timeout = 4000)
    public void get_withUnsupportedUnit_throwsUnsupportedTemporalTypeException() throws Throwable {
        Days zeroDays = Days.ZERO;
        ChronoUnit unsupportedUnit = ChronoUnit.HOURS;

        try {
            zeroDays.get(unsupportedUnit);
            fail("Expecting exception: UnsupportedTemporalTypeException");
        } catch (UnsupportedTemporalTypeException expected) {
            // Message: "Unsupported unit: Hours"
            verifyException("org.threeten.extra.Days", expected);
        }
    }
}
