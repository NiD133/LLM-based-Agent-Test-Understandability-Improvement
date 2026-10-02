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
     * Verifies that Quarter.from() correctly derives Q1 from a JapaneseDate.
     * JapaneseDate uses a non-ISO chronology, so Quarter.from() must first
     * convert it to a LocalDate before extracting the quarter-of-year.
     */
    @Test(timeout = 4000)
    public void test_fromJapaneseDate_derivesQuarterByConvertingToLocalDate() throws Throwable {
        ZoneOffset zoneOffset0 = ZoneOffset.MIN;
        Clock clock0 = MockClock.tickSeconds(zoneOffset0);
        JapaneseDate japaneseDate0 = MockJapaneseDate.now(clock0);

        Quarter quarter0 = Quarter.from(japaneseDate0);

        assertEquals(Quarter.Q1, quarter0);
    }
}
