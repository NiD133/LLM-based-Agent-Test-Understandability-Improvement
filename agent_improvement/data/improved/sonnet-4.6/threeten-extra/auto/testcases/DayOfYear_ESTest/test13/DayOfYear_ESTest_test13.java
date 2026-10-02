package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.temporal.ChronoField;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DayOfYear_ESTest_test13 extends DayOfYear_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_nowReturnsDayOfYear45_andSupportsDayOfYearField() throws Throwable {
        DayOfYear today = DayOfYear.now();

        boolean supportsDayOfYear = today.isSupported(ChronoField.DAY_OF_YEAR);

        assertTrue(supportsDayOfYear);
        assertEquals(45, today.getValue());
    }
}
