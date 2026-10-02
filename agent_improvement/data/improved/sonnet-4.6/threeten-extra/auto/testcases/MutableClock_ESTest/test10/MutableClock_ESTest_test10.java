package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.Instant;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockInstant;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class MutableClock_ESTest_test10 extends MutableClock_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test10_setInstant_updatesClockToMockedNow() throws Throwable {
        // Create a clock starting at the epoch (1970-01-01T00:00:00Z, UTC)
        MutableClock clock = MutableClock.epochUTC();

        // Obtain the mocked "current" instant (controlled by EvoSuite's mock)
        Instant mockedNow = MockInstant.now();

        // Override the clock's instant with the mocked current time
        clock.setInstant(mockedNow);
    }
}
