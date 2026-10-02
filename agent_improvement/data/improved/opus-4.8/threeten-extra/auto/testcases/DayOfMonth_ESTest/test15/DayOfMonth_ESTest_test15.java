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
     * Verifies that {@link DayOfMonth#isSupported(TemporalField)} returns false
     * when queried with a null field, as documented by the method contract.
     * The mocked system clock is fixed so that "today" is the 14th day of the month.
     */
    @Test(timeout = 4000)
    public void isSupported_withNullField_returnsFalse() throws Throwable {
        DayOfMonth today = DayOfMonth.now();

        boolean nullFieldSupported = today.isSupported((TemporalField) null);

        assertEquals("Mocked clock should report the 14th day of the month", 14, today.getValue());
        assertFalse("A null field must never be supported", nullFieldSupported);
    }
}
