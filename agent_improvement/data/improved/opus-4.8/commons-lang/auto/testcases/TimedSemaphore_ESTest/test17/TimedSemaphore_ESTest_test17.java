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
     * The constructor must reject a non-positive time period: building a
     * TimedSemaphore with a period of 0 should fail validation with an
     * IllegalArgumentException ("Time period must be greater than 0.").
     */
    @Test(timeout = 4000)
    public void constructorRejectsZeroTimePeriod() throws Throwable {
        final long invalidTimePeriod = 0L;
        final int limit = 465;

        TimedSemaphore.builder();
        try {
            new TimedSemaphore(invalidTimePeriod, TimeUnit.SECONDS, limit);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // The validation is performed by org.apache.commons.lang3.Validate.
            verifyException("org.apache.commons.lang3.Validate", e);
        }
    }
}
