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

    @Test(timeout = 4000)
    public void fromZoneOffsetThrowsDateTimeException() throws Throwable {
        // ZoneOffset carries no day-of-month information, so DayOfMonth.from() must reject it
        ZoneOffset zoneOffset = ZoneOffset.MAX;
        try {
            DayOfMonth.from(zoneOffset);
            fail("Expecting exception: DateTimeException");
        } catch (DateTimeException e) {
            verifyException("org.threeten.extra.DayOfMonth", e);
        }
    }
}
