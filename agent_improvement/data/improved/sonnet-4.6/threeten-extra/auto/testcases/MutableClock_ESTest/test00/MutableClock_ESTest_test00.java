package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalField;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class MutableClock_ESTest_test00 extends MutableClock_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_setMilliOfSecondFieldToZero_doesNotThrow() throws Throwable {
        MutableClock clock = MutableClock.epochUTC();
        ChronoField milliOfSecond = ChronoField.MILLI_OF_SECOND;
        clock.set((TemporalField) milliOfSecond, 0L);
    }
}
