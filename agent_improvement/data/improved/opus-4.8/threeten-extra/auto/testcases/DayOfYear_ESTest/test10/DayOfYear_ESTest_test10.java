package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.temporal.ChronoField;
import java.time.temporal.UnsupportedTemporalTypeException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DayOfYear_ESTest_test10 extends DayOfYear_ESTest_scaffolding {

    /**
     * DayOfYear only supports the DAY_OF_YEAR field. Requesting the value of any
     * other ChronoField via getLong should fail with UnsupportedTemporalTypeException.
     */
    @Test(timeout = 4000)
    public void getLong_withUnsupportedChronoField_throwsUnsupportedTemporalTypeException() throws Throwable {
        DayOfYear today = DayOfYear.now();
        ChronoField unsupportedField = ChronoField.ALIGNED_DAY_OF_WEEK_IN_MONTH;

        try {
            today.getLong(unsupportedField);
            fail("Expected UnsupportedTemporalTypeException for unsupported field: AlignedDayOfWeekInMonth");
        } catch (UnsupportedTemporalTypeException e) {
            // Message reads: "Unsupported field: AlignedDayOfWeekInMonth"
            verifyException("org.threeten.extra.DayOfYear", e);
        }
    }
}
