package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DayOfYear_ESTest_test16 extends DayOfYear_ESTest_scaffolding {

    /**
     * DayOfYear.from() short-circuits when given a DayOfYear instance directly,
     * returning it as-is rather than re-extracting the field. Verify the value
     * is preserved through the round-trip.
     */
    @Test(timeout = 4000)
    public void test_fromDayOfYear_returnsSameValue() throws Throwable {
        DayOfYear today = DayOfYear.now();
        DayOfYear fromResult = DayOfYear.from(today);
        assertEquals(45, fromResult.getValue());
    }
}
