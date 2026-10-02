package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.temporal.ChronoUnit;
import java.time.temporal.UnsupportedTemporalTypeException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Hours_ESTest_test21 extends Hours_ESTest_scaffolding {

    /**
     * Hours only supports the HOURS unit. Calling get() with any other
     * ChronoUnit (here MINUTES) must throw UnsupportedTemporalTypeException.
     */
    @Test(timeout = 4000)
    public void get_withUnsupportedUnit_throwsUnsupportedTemporalTypeException() throws Throwable {
        Hours zeroHours = Hours.ZERO;

        try {
            zeroHours.get(ChronoUnit.MINUTES);
            fail("Expecting exception: UnsupportedTemporalTypeException");
        } catch (UnsupportedTemporalTypeException e) {
            // Message: "Unsupported unit: Minutes"
            verifyException("org.threeten.extra.Hours", e);
        }
    }
}
