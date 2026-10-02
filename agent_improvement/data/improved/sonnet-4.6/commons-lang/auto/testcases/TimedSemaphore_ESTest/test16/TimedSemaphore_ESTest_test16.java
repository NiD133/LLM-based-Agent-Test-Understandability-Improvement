package org.apache.commons.lang3.concurrent;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TimedSemaphore_ESTest_test16 extends TimedSemaphore_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test16() throws Throwable {
        // A negative period is invalid; get() must reject it immediately.
        TimedSemaphore.Builder builder = TimedSemaphore.builder().setPeriod(-2638L);
        try {
            builder.get();
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            verifyException("org.apache.commons.lang3.Validate", e);
        }
    }
}
