package org.apache.commons.lang3.concurrent;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.concurrent.TimeUnit;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TimedSemaphore_ESTest_test15 extends TimedSemaphore_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void constructorShouldThrowIllegalArgumentExceptionWhenTimePeriodIsNegative() throws Throwable {
        // A negative time period is invalid; the constructor must reject it immediately.
        try {
            new TimedSemaphore((-2502L), TimeUnit.MILLISECONDS, (-4075));
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            verifyException("org.apache.commons.lang3.Validate", e);
        }
    }
}
