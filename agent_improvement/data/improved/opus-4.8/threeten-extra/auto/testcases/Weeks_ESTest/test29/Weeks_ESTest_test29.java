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
     * A {@code Weeks} amount only supports the WEEKS unit, which has an estimated
     * (not exact) duration. {@code Duration.from} therefore cannot convert it and
     * must reject the conversion with an {@link UnsupportedTemporalTypeException}.
     */
    @Test(timeout = 4000)
    public void durationFromWeeksThrowsUnsupportedTemporalType() throws Throwable {
        Weeks oneWeek = Weeks.ONE;

        try {
            Duration.from(oneWeek);
            fail("Expected UnsupportedTemporalTypeException: WEEKS has no exact duration");
        } catch (UnsupportedTemporalTypeException expected) {
            // "Unit must not have an estimated duration"
            verifyException("java.time.Duration", expected);
        }
    }
}
