package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Days_ESTest_test09 extends Days_ESTest_scaffolding {

    // -3380 weeks * 7 days/week = -23660 days
    private static final int NEGATIVE_WEEKS = -3380;
    private static final int EXPECTED_DAYS = -23660;

    @Test(timeout = 4000)
    public void test_ofWeeks_negative_andAbsOfOne() throws Throwable {
        Days negativeDays = Days.ofWeeks(NEGATIVE_WEEKS);
        Days absOfOne = Days.ONE.abs();

        assertEquals(EXPECTED_DAYS, negativeDays.getAmount());
        assertEquals(1, absOfOne.getAmount());
    }
}
