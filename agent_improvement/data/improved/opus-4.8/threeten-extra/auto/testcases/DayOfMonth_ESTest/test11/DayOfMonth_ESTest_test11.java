package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.temporal.UnsupportedTemporalTypeException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.chrono.MockHijrahDate;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DayOfMonth_ESTest_test11 extends DayOfMonth_ESTest_scaffolding {

    /**
     * A {@code DayOfMonth} only carries the day-of-month field; it has no notion of an
     * epoch day. Converting it to a {@code HijrahDate} requires the EPOCH_DAY field, so
     * the conversion must fail with an {@link UnsupportedTemporalTypeException} raised by
     * {@code DayOfMonth}.
     */
    @Test(timeout = 4000)
    public void convertingDayOfMonthToHijrahDateFailsForUnsupportedEpochDay() throws Throwable {
        DayOfMonth dayOfMonth = DayOfMonth.now();

        try {
            MockHijrahDate.from(dayOfMonth);
            fail("Expected UnsupportedTemporalTypeException: DayOfMonth does not support EpochDay");
        } catch (UnsupportedTemporalTypeException expected) {
            // Message: "Unsupported field: EpochDay"
            verifyException("org.threeten.extra.DayOfMonth", expected);
        }
    }
}
