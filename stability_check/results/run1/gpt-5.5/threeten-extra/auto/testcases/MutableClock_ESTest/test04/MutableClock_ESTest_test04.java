package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockInstant;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class MutableClock_ESTest_test04 extends MutableClock_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test04() throws Throwable {
        MutableClock firstEpochClock = MutableClock.epochUTC();
        MutableClock secondEpochClock = MutableClock.epochUTC();

        boolean clocksAreEqual = secondEpochClock.equals(firstEpochClock);

        assertFalse(clocksAreEqual);
    }
}
