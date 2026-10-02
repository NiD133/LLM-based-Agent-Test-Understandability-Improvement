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
public class Weeks_ESTest_test20 extends Weeks_ESTest_scaffolding {

    /**
     * Verifies that {@link Weeks#get(java.time.temporal.TemporalUnit)} rejects any
     * unit other than WEEKS. Here ERAS is unsupported, so the call must throw an
     * UnsupportedTemporalTypeException.
     */
    @Test(timeout = 4000)
    public void get_withUnsupportedUnit_throwsUnsupportedTemporalTypeException() throws Throwable {
        Weeks oneWeek = Weeks.ONE;
        ChronoUnit unsupportedUnit = ChronoUnit.ERAS;

        try {
            oneWeek.get(unsupportedUnit);
            fail("Expecting exception: UnsupportedTemporalTypeException");
        } catch (UnsupportedTemporalTypeException e) {
            // Weeks only supports the WEEKS unit; "Unsupported unit: Eras"
            verifyException("org.threeten.extra.Weeks", e);
        }
    }
}
