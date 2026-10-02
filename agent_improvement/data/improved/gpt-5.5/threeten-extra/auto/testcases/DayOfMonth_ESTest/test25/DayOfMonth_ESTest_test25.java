package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.DateTimeException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockYearMonth;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DayOfMonth_ESTest_test25 extends DayOfMonth_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test25() throws Throwable {
        DayOfMonth dayOnlyTemporal = DayOfMonth.now();

        try {
            MockYearMonth.from(dayOnlyTemporal);
            fail("Expecting exception: DateTimeException");
        } catch (DateTimeException e) {
            verifyException("java.time.YearMonth", e);
        }
    }
}
