package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockInstant;
import org.evosuite.runtime.mock.java.time.MockOffsetDateTime;
import org.evosuite.runtime.mock.java.time.MockZonedDateTime;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Hours_ESTest_test15 extends Hours_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test15() throws Throwable {
        Hours zeroHours = Hours.ZERO;
        boolean isZeroHoursPositive = zeroHours.isPositive();

        assertFalse(isZeroHoursPositive);
        assertEquals(0, zeroHours.getAmount());
    }
}
