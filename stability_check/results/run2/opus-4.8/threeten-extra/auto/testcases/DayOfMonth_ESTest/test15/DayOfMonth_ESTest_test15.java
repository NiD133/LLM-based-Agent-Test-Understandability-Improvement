package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.temporal.TemporalField;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DayOfMonth_ESTest_test15 extends DayOfMonth_ESTest_scaffolding {

    /**
     * A null field is never supported, and the current day-of-month reflects
     * the mocked system clock (fixed at the 14th in this test environment).
     */
    @Test(timeout = 4000)
    public void isSupportedReturnsFalseForNullField() throws Throwable {
        DayOfMonth today = DayOfMonth.now();

        boolean nullFieldSupported = today.isSupported((TemporalField) null);

        assertFalse(nullFieldSupported);
        assertEquals(14, today.getValue());
    }
}
