package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.temporal.TemporalField;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DayOfYear_ESTest_test14 extends DayOfYear_ESTest_scaffolding {

    /**
     * A null field is never supported, even though the underlying
     * day-of-year value (driven by the mocked system clock) is valid.
     */
    @Test(timeout = 4000)
    public void isSupportedReturnsFalseForNullField() throws Throwable {
        DayOfYear today = DayOfYear.now();

        boolean nullFieldSupported = today.isSupported((TemporalField) null);

        assertEquals(45, today.getValue());
        assertFalse(nullFieldSupported);
    }
}
