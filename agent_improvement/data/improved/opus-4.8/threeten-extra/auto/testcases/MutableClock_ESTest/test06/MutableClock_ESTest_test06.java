package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class MutableClock_ESTest_test06 extends MutableClock_ESTest_scaffolding {

    /**
     * Verifies that {@link MutableClock#equals(Object)} is reflexive:
     * a clock must always be equal to itself.
     */
    @Test(timeout = 4000)
    public void equals_returnsTrue_whenComparedToItself() throws Throwable {
        MutableClock clock = MutableClock.epochUTC();

        boolean equalToItself = clock.equals(clock);

        assertTrue("A clock should be equal to itself", equalToItself);
    }
}
