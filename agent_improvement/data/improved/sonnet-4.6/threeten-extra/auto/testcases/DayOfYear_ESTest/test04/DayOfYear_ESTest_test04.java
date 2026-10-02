package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.DateTimeException;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.chrono.HijrahDate;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.chrono.MockHijrahDate;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DayOfYear_ESTest_test04 extends DayOfYear_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void adjustInto_nonIsoHijrahDate_throwsDateTimeException() throws Throwable {
        DayOfYear currentDayOfYear = DayOfYear.now();
        ZoneOffset minZoneOffset = ZoneOffset.MIN;
        HijrahDate hijrahDate = MockHijrahDate.now((ZoneId) minZoneOffset);

        try {
            currentDayOfYear.adjustInto(hijrahDate);
            fail("Expecting exception: DateTimeException");
        } catch (DateTimeException e) {
            //
            // Adjustment only supported on ISO date-time
            //
            verifyException("org.threeten.extra.DayOfYear", e);
        }
    }
}
