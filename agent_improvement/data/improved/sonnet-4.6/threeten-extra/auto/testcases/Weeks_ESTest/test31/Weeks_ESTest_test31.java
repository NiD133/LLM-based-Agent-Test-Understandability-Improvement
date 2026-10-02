package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Weeks_ESTest_test31 extends Weeks_ESTest_scaffolding {

    // Weeks.ONE.toString() should produce the ISO-8601 week-based duration format "P1W"
    @Test(timeout = 4000)
    public void test_oneWeekToStringReturnsISO8601Format() throws Throwable {
        Weeks oneWeek = Weeks.ONE;
        String isoString = oneWeek.toString();
        assertEquals("P1W", isoString);
    }
}
