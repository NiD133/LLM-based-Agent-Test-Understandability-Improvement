package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.DateTimeException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DayOfYear_ESTest_test00 extends DayOfYear_ESTest_scaffolding {

    private static final int NEGATIVE_DAY_VALUE = -408;

    @Test(timeout = 4000)
    public void test_ofNegativeDay_throwsDateTimeException() throws Throwable {
        try {
            DayOfYear.of(NEGATIVE_DAY_VALUE);
            fail("Expecting exception: DateTimeException");
        } catch (DateTimeException e) {
            verifyException("org.threeten.extra.DayOfYear", e);
        }
    }
}
