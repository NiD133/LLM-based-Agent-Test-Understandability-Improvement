package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.fail;
import static org.evosuite.runtime.EvoAssertions.verifyException;
import java.time.temporal.ChronoUnit;
import java.time.temporal.UnsupportedTemporalTypeException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Seconds_ESTest_test20 extends Seconds_ESTest_scaffolding {

    /**
     * Seconds only supports the SECONDS unit. Calling get() with any other unit,
     * such as ERAS, must fail with an UnsupportedTemporalTypeException.
     */
    @Test(timeout = 4000)
    public void get_withUnsupportedUnit_throwsUnsupportedTemporalTypeException() throws Throwable {
        Seconds zeroSeconds = Seconds.ZERO;
        ChronoUnit unsupportedUnit = ChronoUnit.ERAS;

        try {
            zeroSeconds.get(unsupportedUnit);
            fail("Expecting exception: UnsupportedTemporalTypeException");
        } catch (UnsupportedTemporalTypeException e) {
            // Message: "Unsupported unit: Eras"
            verifyException("org.threeten.extra.Seconds", e);
        }
    }
}
