package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.fail;
import static org.evosuite.runtime.EvoAssertions.verifyException;
import java.time.DateTimeException;
import java.time.Period;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Years_ESTest_test24 extends Years_ESTest_scaffolding {

    /**
     * Years.from rejects a Period whose months component is not a whole
     * number of years. Here the period contains -2144 months, which cannot
     * be converted to years without a remainder, so a DateTimeException is thrown.
     */
    @Test(timeout = 4000)
    public void from_periodWithNonWholeYearMonths_throwsDateTimeException() throws Throwable {
        Period periodWithMonths = Period.of(-2144, -2144, -2144);

        try {
            Years.from(periodWithMonths);
            fail("Expecting exception: DateTimeException");
        } catch (DateTimeException e) {
            // Amount could not be converted to a whole number of years: -2144 Months
            verifyException("org.threeten.extra.Years", e);
        }
    }
}
