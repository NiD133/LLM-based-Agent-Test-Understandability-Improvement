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
public class Days_ESTest_test09 extends Days_ESTest_scaffolding {

    private static final int WEEKS_TO_CONVERT = -3380;
    private static final int EXPECTED_DAYS_FROM_WEEKS = -23660;
    private static final int EXPECTED_ABSOLUTE_ONE_DAY = 1;

    @Test(timeout = 4000)
    public void test09() throws Throwable {
        Days convertedDays = Days.ofWeeks(WEEKS_TO_CONVERT);
        Days absoluteOneDay = convertedDays.ONE.abs();

        assertEquals(EXPECTED_DAYS_FROM_WEEKS, convertedDays.getAmount());
        assertEquals(EXPECTED_ABSOLUTE_ONE_DAY, absoluteOneDay.getAmount());
    }
}
