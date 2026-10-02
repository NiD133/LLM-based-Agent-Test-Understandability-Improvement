package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.ZonedDateTime;
import java.time.temporal.Temporal;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockZonedDateTime;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Seconds_ESTest_test05 extends Seconds_ESTest_scaffolding {

    /**
     * Verifies that subtracting the ZERO amount from a temporal is a no-op:
     * because ZERO holds 0 seconds, subtractFrom returns the exact same temporal
     * instance unchanged. The unrelated 1507-hour amount is also checked to
     * confirm ofHours converts hours to seconds (1507 * 3600 = 5425200).
     */
    @Test(timeout = 4000)
    public void test05() throws Throwable {
        Seconds hoursAmount = Seconds.ofHours(1507);
        ZonedDateTime dateTime = MockZonedDateTime.now();

        Temporal result = Seconds.ZERO.subtractFrom(dateTime);

        assertSame("subtracting zero seconds must return the same temporal instance", dateTime, result);
        assertEquals("1507 hours expressed in seconds", 5425200, hoursAmount.getAmount());
    }
}
