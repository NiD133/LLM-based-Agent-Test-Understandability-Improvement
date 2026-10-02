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
     * Verifies that {@link DayOfYear#from(java.time.temporal.TemporalAccessor)} returns
     * a value equal to the one already held by the source {@code DayOfYear}.
     * <p>
     * Under the EvoSuite mocked clock the current date resolves to the 45th day of
     * the year, so {@code DayOfYear.now()} yields day 45 and {@code from} must echo it.
     */
    @Test(timeout = 4000)
    public void from_existingDayOfYear_returnsSameValue() throws Throwable {
        DayOfYear today = DayOfYear.now();

        DayOfYear converted = DayOfYear.from(today);

        assertEquals(45, converted.getValue());
    }
}
