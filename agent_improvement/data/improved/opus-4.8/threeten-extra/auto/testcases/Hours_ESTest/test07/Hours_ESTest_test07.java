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
public class Hours_ESTest_test07 extends Hours_ESTest_scaffolding {

    /**
     * Verifies that adding a zero-hour amount to a temporal leaves it unchanged.
     * Since {@link Hours#addTo(Temporal)} skips the addition when the amount is
     * zero, it must return the very same temporal instance that was passed in.
     */
    @Test(timeout = 4000)
    public void addTo_withZeroHours_returnsSameTemporalInstance() throws Throwable {
        Hours zeroHours = Hours.ZERO;
        ZonedDateTime startDateTime = MockZonedDateTime.now();

        Temporal result = zeroHours.addTo(startDateTime);

        assertSame(startDateTime, result);
    }
}
