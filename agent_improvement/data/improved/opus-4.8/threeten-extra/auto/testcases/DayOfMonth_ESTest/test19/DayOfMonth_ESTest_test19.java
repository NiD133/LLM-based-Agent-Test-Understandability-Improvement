package org.threeten.extra;

import static org.junit.Assert.assertEquals;

import java.time.ZoneId;
import java.time.ZoneOffset;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DayOfMonth_ESTest_test19 extends DayOfMonth_ESTest_scaffolding {

    /**
     * Verifies that {@link DayOfMonth#now(ZoneId)} reads the current day-of-month
     * from the system clock for the given time-zone offset.
     * <p>
     * EvoSuite mocks the JVM clock to a fixed instant, so the day-of-month
     * resolves deterministically to the 14th.
     */
    @Test(timeout = 4000)
    public void nowWithZoneOffsetReturnsCurrentDayOfMonth() throws Throwable {
        ZoneOffset offsetPlus0101 = ZoneOffset.ofHoursMinutes(1, 1);

        DayOfMonth currentDay = DayOfMonth.now((ZoneId) offsetPlus0101);

        assertEquals(14, currentDay.getValue());
    }
}
