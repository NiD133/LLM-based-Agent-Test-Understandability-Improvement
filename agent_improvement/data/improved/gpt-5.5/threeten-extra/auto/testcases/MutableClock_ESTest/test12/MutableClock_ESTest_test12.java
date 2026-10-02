package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.Duration;
import java.time.temporal.TemporalAmount;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockInstant;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class MutableClock_ESTest_test12 extends MutableClock_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test12() throws Throwable {
        MutableClock epochClock = MutableClock.epochUTC();
        Duration zeroDuration = Duration.ZERO;

        epochClock.add((TemporalAmount) zeroDuration);
    }
}
