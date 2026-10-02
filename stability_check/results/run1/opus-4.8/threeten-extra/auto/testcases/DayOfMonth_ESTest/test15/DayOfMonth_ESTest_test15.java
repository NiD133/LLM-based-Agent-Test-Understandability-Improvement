package org.threeten.extra;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.time.temporal.TemporalField;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DayOfMonth_ESTest_test15 extends DayOfMonth_ESTest_scaffolding {

    /**
     * A null field is never supported, and querying support must not affect the
     * day-of-month value. Under the mocked clock, {@link DayOfMonth#now()} yields
     * the 14th.
     */
    @Test(timeout = 4000)
    public void isSupportedReturnsFalseForNullField() throws Throwable {
        DayOfMonth today = DayOfMonth.now();

        boolean nullFieldSupported = today.isSupported((TemporalField) null);

        assertFalse(nullFieldSupported);
        assertEquals(14, today.getValue());
    }
}
