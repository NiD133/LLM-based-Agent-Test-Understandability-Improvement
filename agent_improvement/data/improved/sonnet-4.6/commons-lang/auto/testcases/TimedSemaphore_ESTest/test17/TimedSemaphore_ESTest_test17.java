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
public class TimedSemaphore_ESTest_test17 extends TimedSemaphore_ESTest_scaffolding {

    /**
     * Verifies that constructing a TimedSemaphore with a zero time period throws
     * IllegalArgumentException, and that the builder() factory method is accessible.
     */
    @Test(timeout = 4000)
    public void test_constructorRejectsZeroTimePeriod() throws Throwable {
        // Verify the builder factory method is callable (does not throw)
        TimedSemaphore.builder();

        // A period of 0 is invalid; the constructor must reject it
        try {
            new TimedSemaphore(0L, TimeUnit.SECONDS, 465);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            verifyException("org.apache.commons.lang3.Validate", e);
        }
    }
}
