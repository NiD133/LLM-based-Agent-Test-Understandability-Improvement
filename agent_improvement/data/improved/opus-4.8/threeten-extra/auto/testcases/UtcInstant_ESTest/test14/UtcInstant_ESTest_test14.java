package org.threeten.extra.scale;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class UtcInstant_ESTest_test14 extends UtcInstant_ESTest_scaffolding {

    /**
     * Parsing an ISO-8601 UTC timestamp should split it into a Modified Julian Day
     * (the calendar date) and a nano-of-day (the time within that day).
     */
    @Test(timeout = 4000)
    public void parseExtractsModifiedJulianDayAndNanoOfDay() throws Throwable {
        // A far-future date with a time one nanosecond before two seconds to midnight.
        UtcInstant parsed = UtcInstant.parse("+2739877-01-02T23:59:59.999999997Z");

        long expectedNanoOfDay = 86399999999997L;   // 23:59:59.999999997 expressed in nanoseconds
        long expectedModifiedJulianDay = 1000040586L; // calendar date +2739877-01-02 as an MJD

        assertEquals(expectedNanoOfDay, parsed.getNanoOfDay());
        assertEquals(expectedModifiedJulianDay, parsed.getModifiedJulianDay());
    }
}
