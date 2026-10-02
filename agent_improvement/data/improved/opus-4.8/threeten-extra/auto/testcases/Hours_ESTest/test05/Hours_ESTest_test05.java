package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.OffsetDateTime;
import java.time.temporal.Temporal;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockOffsetDateTime;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Hours_ESTest_test05 extends Hours_ESTest_scaffolding {

    /**
     * Subtracting a zero-hour amount from a temporal must leave it unchanged.
     * Hours.subtractFrom only adjusts the temporal when the amount is non-zero,
     * so for Hours.ZERO it returns the very same instance it was given.
     */
    @Test(timeout = 4000)
    public void subtractFromZeroHoursReturnsSameTemporal() throws Throwable {
        Hours zeroHours = Hours.ZERO;
        OffsetDateTime dateTime = MockOffsetDateTime.now();

        Temporal result = zeroHours.subtractFrom(dateTime);

        assertSame(dateTime, result);
    }
}
