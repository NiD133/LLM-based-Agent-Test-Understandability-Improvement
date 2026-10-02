package org.threeten.extra;

import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;

import java.time.Month;

import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockClock;
import org.evosuite.runtime.mock.java.time.MockLocalDateTime;
import org.evosuite.runtime.mock.java.time.chrono.MockHijrahDate;
import org.evosuite.runtime.mock.java.time.chrono.MockJapaneseDate;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Quarter_ESTest_test04 extends Quarter_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test04() throws Throwable {
        Quarter secondQuarter = Quarter.Q2;

        Month firstMonthOfSecondQuarter = secondQuarter.firstMonth();

        assertEquals(Month.APRIL, firstMonthOfSecondQuarter);
    }
}
