package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.DateTimeException;
import java.time.ZoneOffset;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DayOfMonth_ESTest_test01 extends DayOfMonth_ESTest_scaffolding {

    /**
     * A {@link ZoneOffset} carries no day-of-month information, so converting one
     * via {@link DayOfMonth#from} must fail with a {@link DateTimeException}.
     */
    @Test(timeout = 4000)
    public void from_zoneOffsetWithoutDayOfMonth_throwsDateTimeException() throws Throwable {
        ZoneOffset offsetWithoutDate = ZoneOffset.MAX;

        try {
            DayOfMonth.from(offsetWithoutDate);
            fail("Expected DateTimeException because a ZoneOffset has no day-of-month");
        } catch (DateTimeException expected) {
            // Message: "Unable to obtain DayOfMonth from TemporalAccessor: +18:00 of type java.time.ZoneOffset"
            verifyException("org.threeten.extra.DayOfMonth", expected);
        }
    }
}
