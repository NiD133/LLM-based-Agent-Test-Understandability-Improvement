package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockInstant;
import org.evosuite.runtime.mock.java.time.MockZonedDateTime;
import org.evosuite.runtime.mock.java.time.chrono.MockMinguoDate;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Days_ESTest_test28 extends Days_ESTest_scaffolding {

    private static final int WEEKS = -25018;
    private static final int DAYS_PER_WEEK = 7;
    private static final int EXPECTED_DAYS = WEEKS * DAYS_PER_WEEK;

    @Test(timeout = 4000)
    public void test28() throws Throwable {
        Days days = Days.ofWeeks(WEEKS);

        days.getUnits();

        assertEquals(EXPECTED_DAYS, days.getAmount());
    }
}
