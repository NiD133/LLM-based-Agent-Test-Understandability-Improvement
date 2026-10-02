package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.ZoneId;
import java.time.ZoneOffset;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class InternationalFixedChronology_ESTest_test15 extends InternationalFixedChronology_ESTest_scaffolding {

    /**
     * Verifies that:
     *  - the current InternationalFixedDate has a standard (non-leap) year length of 365 days, and
     *  - year 100 is reported as a non-leap year by the chronology.
     */
    @Test(timeout = 4000)
    public void test15() throws Throwable {
        ZoneId zone = ZoneOffset.MIN;
        InternationalFixedDate today = InternationalFixedDate.now(zone);
        InternationalFixedChronology chronology = today.getChronology();

        boolean year100IsLeap = chronology.isLeapYear(100L);

        assertEquals(365, today.lengthOfYear());
        assertFalse(year100IsLeap);
    }
}
