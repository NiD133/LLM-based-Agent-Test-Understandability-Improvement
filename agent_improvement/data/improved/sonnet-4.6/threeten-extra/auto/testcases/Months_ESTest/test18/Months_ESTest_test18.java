package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalUnit;
import java.time.temporal.UnsupportedTemporalTypeException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Months_ESTest_test18 extends Months_ESTest_scaffolding {

    /**
     * Months.get() only supports the MONTHS unit; querying it with WEEKS
     * (the base unit of ALIGNED_WEEK_OF_MONTH) must throw UnsupportedTemporalTypeException.
     */
    @Test(timeout = 4000)
    public void test18_getWithWeeksUnitThrowsUnsupportedTemporalTypeException() throws Throwable {
        Months oneMonth = Months.ONE;
        ChronoField alignedWeekOfMonth = ChronoField.ALIGNED_WEEK_OF_MONTH;
        TemporalUnit weeksUnit = alignedWeekOfMonth.getBaseUnit();
        try {
            oneMonth.get(weeksUnit);
            fail("Expecting exception: UnsupportedTemporalTypeException");
        } catch (UnsupportedTemporalTypeException e) {
            //
            // Unsupported unit: Weeks
            //
            verifyException("org.threeten.extra.Months", e);
        }
    }
}
