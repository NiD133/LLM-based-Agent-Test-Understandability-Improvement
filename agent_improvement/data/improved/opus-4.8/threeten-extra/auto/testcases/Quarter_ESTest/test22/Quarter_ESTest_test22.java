package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.Clock;
import java.time.ZoneOffset;
import java.time.chrono.JapaneseDate;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockClock;
import org.evosuite.runtime.mock.java.time.chrono.MockJapaneseDate;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Quarter_ESTest_test22 extends Quarter_ESTest_scaffolding {

    /**
     * Quarter.from should accept a non-ISO temporal (here a JapaneseDate) by
     * converting it to a LocalDate first, and return the matching quarter.
     * The mocked clock fixes "now" to a date that falls in the first quarter,
     * so the resulting Quarter is Q1.
     */
    @Test(timeout = 4000)
    public void from_japaneseDate_returnsQuarterOfCurrentDate() throws Throwable {
        Clock fixedClock = MockClock.tickSeconds(ZoneOffset.MIN);
        JapaneseDate today = MockJapaneseDate.now(fixedClock);

        Quarter quarter = Quarter.from(today);

        assertEquals(Quarter.Q1, quarter);
    }
}
