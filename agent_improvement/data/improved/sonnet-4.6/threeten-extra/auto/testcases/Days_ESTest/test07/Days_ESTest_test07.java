package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.ZonedDateTime;
import java.time.temporal.Temporal;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockZonedDateTime;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Days_ESTest_test07 extends Days_ESTest_scaffolding {

    // Verifies that Days.between a temporal and itself yields zero days,
    // and that adding zero days to a temporal returns the exact same object.
    @Test(timeout = 4000)
    public void test07() throws Throwable {
        ZonedDateTime now = MockZonedDateTime.now();
        Days zeroDays = Days.between(now, now);
        Temporal afterAddingZero = Days.ZERO.addTo(now);
        assertEquals(0, zeroDays.getAmount());
        assertSame(afterAddingZero, now);
    }
}
