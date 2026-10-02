package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Quarter_ESTest_test23 extends Quarter_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void testOfMonth_aprilMonthNumber_returnsQ2() throws Throwable {
        // April is month 4, which belongs to Q2 (April–June)
        Quarter aprilQuarter = Quarter.ofMonth(4);
        assertEquals(Quarter.Q2, aprilQuarter);
    }
}
