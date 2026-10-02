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
public class Days_ESTest_test07 extends Days_ESTest_scaffolding {

    /**
     * The number of days between a date-time and itself is zero, and adding a
     * zero-day amount to a temporal leaves the temporal unchanged (the exact
     * same instance is returned).
     */
    @Test(timeout = 4000)
    public void betweenSameInstant_isZero_andAddingZeroReturnsSameTemporal() throws Throwable {
        ZonedDateTime sameInstant = MockZonedDateTime.now();

        Days daysBetween = Days.between(sameInstant, sameInstant);
        assertEquals(0, daysBetween.getAmount());

        Temporal result = Days.ZERO.addTo(sameInstant);
        assertSame(sameInstant, result);
    }
}
