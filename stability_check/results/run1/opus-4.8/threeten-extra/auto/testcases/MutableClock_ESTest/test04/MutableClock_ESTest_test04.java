package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class MutableClock_ESTest_test04 extends MutableClock_ESTest_scaffolding {

    /**
     * Two clocks created by separate {@code epochUTC()} calls are not equal,
     * even though they share the same instant and time-zone. Equality requires
     * shared updates (the same internal instant holder), which independently
     * created clocks do not have.
     */
    @Test(timeout = 4000)
    public void independentlyCreatedEpochClocksAreNotEqual() throws Throwable {
        MutableClock firstEpochClock = MutableClock.epochUTC();
        MutableClock secondEpochClock = MutableClock.epochUTC();

        boolean clocksAreEqual = secondEpochClock.equals(firstEpochClock);

        assertFalse(clocksAreEqual);
    }
}
