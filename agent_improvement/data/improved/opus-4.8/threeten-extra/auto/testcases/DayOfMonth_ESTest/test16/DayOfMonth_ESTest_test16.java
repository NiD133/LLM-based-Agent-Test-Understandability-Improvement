package org.threeten.extra;

import static org.junit.Assert.assertEquals;

import java.time.chrono.ThaiBuddhistDate;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.chrono.MockThaiBuddhistDate;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DayOfMonth_ESTest_test16 extends DayOfMonth_ESTest_scaffolding {

    /**
     * DayOfMonth.from should derive the day-of-month from a non-ISO temporal
     * (a ThaiBuddhistDate) by converting it to the equivalent ISO date.
     * The mocked clock fixes "now" to the 14th of the month.
     */
    @Test(timeout = 4000)
    public void from_thaiBuddhistDate_extractsDayOfMonth() throws Throwable {
        ThaiBuddhistDate mockedToday = MockThaiBuddhistDate.now();

        DayOfMonth dayOfMonth = DayOfMonth.from(mockedToday);

        assertEquals(14, dayOfMonth.getValue());
    }
}
