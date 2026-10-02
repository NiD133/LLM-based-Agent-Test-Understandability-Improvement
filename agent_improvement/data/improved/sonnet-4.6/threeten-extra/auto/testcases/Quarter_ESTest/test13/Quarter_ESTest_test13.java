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

    @Test(timeout = 4000)
    public void test_get_withUnsupportedChronoField_throwsUnsupportedTemporalTypeException() throws Throwable {
        Quarter quarter = Quarter.Q2;
        // CLOCK_HOUR_OF_AMPM is a ChronoField not supported by Quarter
        try {
            quarter.get(ChronoField.CLOCK_HOUR_OF_AMPM);
            fail("Expecting exception: UnsupportedTemporalTypeException");
        } catch (UnsupportedTemporalTypeException e) {
            verifyException("org.threeten.extra.Quarter", e);
        }
    }
}
