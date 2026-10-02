package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.DateTimeException;
import java.time.Period;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Years_ESTest_test24 extends Years_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_fromPeriodWithNonZeroMonths_throwsDateTimeException() throws Throwable {
        // A Period with a months component cannot be exactly converted to a whole number of years
        Period periodWithMonths = Period.of(-2144, -2144, -2144);

        try {
            Years.from(periodWithMonths);
            fail("Expecting exception: DateTimeException");
        } catch (DateTimeException e) {
            // -2144 months is not a whole-year multiple, so conversion must fail
            verifyException("org.threeten.extra.Years", e);
        }
    }
}
