package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.ZoneId;
import java.time.ZoneOffset;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DayOfMonth_ESTest_test19 extends DayOfMonth_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_nowWithZoneOffset_returnsExpectedDayOfMonth() throws Throwable {
        // ZoneOffset implements ZoneId, so it can be passed to DayOfMonth.now(ZoneId)
        ZoneOffset zoneOffset0 = ZoneOffset.ofHoursMinutes(1, 1);
        DayOfMonth dayOfMonth0 = DayOfMonth.now((ZoneId) zoneOffset0);
        assertEquals(14, dayOfMonth0.getValue());
    }
}
