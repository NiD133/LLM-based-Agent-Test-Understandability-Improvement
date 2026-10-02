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

    /**
     * {@code adjustInto} must reject temporals that do not use the ISO calendar
     * system. A {@link HijrahDate} uses the Hijrah chronology, so adjusting it
     * is expected to fail with a {@link DateTimeException}.
     */
    @Test(timeout = 4000)
    public void adjustInto_withNonIsoTemporal_throwsDateTimeException() throws Throwable {
        DayOfYear dayOfYear = DayOfYear.now();
        HijrahDate nonIsoDate = MockHijrahDate.now((ZoneId) ZoneOffset.MIN);

        try {
            dayOfYear.adjustInto(nonIsoDate);
            fail("Expecting exception: DateTimeException");
        } catch (DateTimeException e) {
            // Message: "Adjustment only supported on ISO date-time"
            verifyException("org.threeten.extra.DayOfYear", e);
        }
    }
}
