package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class MutableClock_ESTest_test09 extends MutableClock_ESTest_scaffolding {

    /**
     * Verifies that {@link MutableClock#toString()} renders a clock created via
     * {@link MutableClock#epochUTC()} as its epoch instant (1970-01-01T00:00:00Z)
     * together with the UTC zone, formatted as {@code MutableClock[<instant>,<zone>]}.
     */
    @Test(timeout = 4000)
    public void epochUtcClock_toString_showsEpochInstantAndUtcZone() throws Throwable {
        MutableClock epochUtcClock = MutableClock.epochUTC();

        String description = epochUtcClock.toString();

        assertEquals("MutableClock[1970-01-01T00:00:00Z,Z]", description);
    }
}
