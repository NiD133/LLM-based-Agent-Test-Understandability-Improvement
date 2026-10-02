package org.threeten.extra;

import org.junit.Test;

import java.time.temporal.ChronoField;
import java.time.temporal.TemporalField;

import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockInstant;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class MutableClock_ESTest_test00 extends MutableClock_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test00() throws Throwable {
        MutableClock clockAtEpochUtc = MutableClock.epochUTC();
        ChronoField millisecondOfSecond = ChronoField.MILLI_OF_SECOND;

        // The epoch instant is already at millisecond zero; this verifies the field update accepts that value.
        clockAtEpochUtc.set((TemporalField) millisecondOfSecond, 0L);
    }
}
