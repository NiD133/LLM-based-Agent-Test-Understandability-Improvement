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
     * Adjusting a date-time into Q4 shifts it to the fourth quarter, so the
     * adjusted result must differ from the original date-time (which was not
     * already in Q4 at the mocked "now").
     */
    @Test(timeout = 4000)
    public void adjustIntoQ4ReturnsDifferentTemporal() throws Throwable {
        Quarter fourthQuarter = Quarter.Q4;
        LocalDateTime originalDateTime = MockLocalDateTime.now();

        Temporal adjustedDateTime = fourthQuarter.adjustInto(originalDateTime);

        assertFalse(adjustedDateTime.equals((Object) originalDateTime));
    }
}
