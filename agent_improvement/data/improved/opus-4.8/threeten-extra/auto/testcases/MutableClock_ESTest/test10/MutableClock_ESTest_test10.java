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

    /**
     * Verifies that the instant of a MutableClock can be overridden via
     * {@link MutableClock#setInstant(Instant)} with a non-null instant,
     * which completes without throwing.
     */
    @Test(timeout = 4000)
    public void setInstantWithCurrentInstantSucceeds() throws Throwable {
        MutableClock clock = MutableClock.epochUTC();
        Instant newInstant = MockInstant.now();

        clock.setInstant(newInstant);
    }
}
