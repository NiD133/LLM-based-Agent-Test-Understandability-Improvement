package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.Duration;
import java.time.temporal.UnsupportedTemporalTypeException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Weeks_ESTest_test29 extends Weeks_ESTest_scaffolding {

    /**
     * Duration.from() requires units with an exact (non-estimated) duration.
     * Weeks are calendar-based and therefore considered estimated by the Java
     * time API, so converting Weeks to a Duration must throw.
     */
    @Test(timeout = 4000)
    public void test_convertingWeeksToDuration_throwsUnsupportedTemporalTypeException() throws Throwable {
        try {
            Duration.from(Weeks.ONE);
            fail("Expecting exception: UnsupportedTemporalTypeException");
        } catch (UnsupportedTemporalTypeException e) {
            verifyException("java.time.Duration", e);
        }
    }
}
