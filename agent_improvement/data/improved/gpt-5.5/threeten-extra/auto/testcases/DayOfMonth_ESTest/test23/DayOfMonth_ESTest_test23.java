package org.threeten.extra;

import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockClock;
import org.evosuite.runtime.mock.java.time.MockLocalDateTime;
import org.evosuite.runtime.mock.java.time.MockOffsetDateTime;
import org.evosuite.runtime.mock.java.time.MockYearMonth;
import org.evosuite.runtime.mock.java.time.chrono.MockHijrahDate;
import org.evosuite.runtime.mock.java.time.chrono.MockJapaneseDate;
import org.evosuite.runtime.mock.java.time.chrono.MockMinguoDate;
import org.evosuite.runtime.mock.java.time.chrono.MockThaiBuddhistDate;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DayOfMonth_ESTest_test23 extends DayOfMonth_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test23() throws Throwable {
        ZoneOffset utcOffset = ZoneOffset.UTC;
        Clock utcClock = MockClock.system(utcOffset);
        LocalDateTime currentDateTime = MockLocalDateTime.now(utcClock);

        DayOfMonth dayOfMonth = DayOfMonth.from(currentDateTime);

        dayOfMonth.hashCode();
        assertEquals(14, dayOfMonth.getValue());
    }
}
