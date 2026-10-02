package org.threeten.extra;

import org.junit.Test;
import java.time.Duration;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class MutableClock_ESTest_test12 extends MutableClock_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_addZeroDurationToEpochClockDoesNotThrow() throws Throwable {
        MutableClock clock = MutableClock.epochUTC();
        clock.add(Duration.ZERO);
    }
}
