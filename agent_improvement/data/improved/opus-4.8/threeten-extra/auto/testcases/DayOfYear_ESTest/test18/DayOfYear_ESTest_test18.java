package org.threeten.extra;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

/**
 * Tests for {@link DayOfYear}.
 *
 * <p>Under EvoSuite the system clock is mocked to a fixed instant, so
 * {@link DayOfYear#now()} deterministically resolves to the 45th day of the year.</p>
 */
@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DayOfYear_ESTest_test18 extends DayOfYear_ESTest_scaffolding {

    /**
     * {@link DayOfYear#now()} reads the (mocked) system clock and
     * {@link DayOfYear#getValue()} returns the matching day-of-year value.
     */
    @Test(timeout = 4000)
    public void now_returnsDayOfYearFromMockedClock() throws Throwable {
        DayOfYear today = DayOfYear.now();

        int dayOfYearValue = today.getValue();

        assertEquals(45, dayOfYearValue);
    }
}
