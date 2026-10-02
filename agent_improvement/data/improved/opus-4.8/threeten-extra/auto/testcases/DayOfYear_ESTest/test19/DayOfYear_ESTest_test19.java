package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DayOfYear_ESTest_test19 extends DayOfYear_ESTest_scaffolding {

    /**
     * Under the EvoSuite mocked clock, the current date falls on the 45th day
     * of the year, so {@link DayOfYear#now()} reports a value of 45. Calling
     * {@code hashCode()} must not change that value.
     */
    @Test(timeout = 4000)
    public void nowReportsMockedDayOfYearAndHashCodeIsHarmless() throws Throwable {
        DayOfYear currentDayOfYear = DayOfYear.now();

        currentDayOfYear.hashCode();

        assertEquals(45, currentDayOfYear.getValue());
    }
}
