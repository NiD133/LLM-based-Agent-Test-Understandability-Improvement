package org.threeten.extra;

import static org.junit.Assert.fail;
import static org.evosuite.runtime.EvoAssertions.verifyException;

import java.time.DateTimeException;
import java.time.ZoneOffset;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DayOfYear_ESTest_test01 extends DayOfYear_ESTest_scaffolding {

    /**
     * A {@link ZoneOffset} carries no day-of-year information, so attempting to
     * build a {@link DayOfYear} from it must fail with a {@link DateTimeException}
     * thrown by {@code DayOfYear.from(...)}.
     */
    @Test(timeout = 4000)
    public void from_zoneOffsetWithoutDayOfYear_throwsDateTimeException() throws Throwable {
        ZoneOffset zoneOffsetWithoutDayOfYear = ZoneOffset.MAX;

        try {
            DayOfYear.from(zoneOffsetWithoutDayOfYear);
            fail("Expected a DateTimeException because ZoneOffset has no day-of-year");
        } catch (DateTimeException expected) {
            // Message: "Unable to obtain DayOfYear from TemporalAccessor: +18:00 of type java.time.ZoneOffset"
            verifyException("org.threeten.extra.DayOfYear", expected);
        }
    }
}
