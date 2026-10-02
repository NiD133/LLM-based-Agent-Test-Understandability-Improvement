package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.temporal.Temporal;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockLocalDateTime;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Quarter_ESTest_test01 extends Quarter_ESTest_scaffolding {

    /**
     * Adjusting a date-time (whose current quarter is not Q4) into Q4 must
     * produce a different date-time, since the quarter-of-year actually changes.
     */
    @Test(timeout = 4000)
    public void adjustIntoQ4ReturnsDifferentDateTime() throws Throwable {
        Quarter fourthQuarter = Quarter.Q4;
        LocalDateTime currentDateTime = MockLocalDateTime.now();

        Temporal adjustedDateTime = fourthQuarter.adjustInto(currentDateTime);

        assertFalse(adjustedDateTime.equals(currentDateTime));
    }
}
