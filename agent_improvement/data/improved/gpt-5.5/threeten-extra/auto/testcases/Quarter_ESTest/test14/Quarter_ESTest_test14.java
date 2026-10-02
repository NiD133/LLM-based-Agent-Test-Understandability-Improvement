package org.threeten.extra;

import static org.evosuite.runtime.EvoAssertions.*;
import static org.junit.Assert.*;

import java.time.temporal.ChronoField;
import java.time.temporal.UnsupportedTemporalTypeException;

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
public class Quarter_ESTest_test14 extends Quarter_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test14() throws Throwable {
        Quarter firstQuarter = Quarter.Q1;
        ChronoField unsupportedField = ChronoField.AMPM_OF_DAY;

        try {
            firstQuarter.range(unsupportedField);
            fail("Expecting exception: UnsupportedTemporalTypeException");
        } catch (UnsupportedTemporalTypeException exception) {
            verifyException("org.threeten.extra.Quarter", exception);
        }
    }
}
