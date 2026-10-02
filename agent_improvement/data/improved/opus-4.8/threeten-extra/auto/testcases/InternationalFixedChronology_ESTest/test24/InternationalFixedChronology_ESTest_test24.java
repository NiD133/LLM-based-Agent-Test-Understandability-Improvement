package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.ZoneId;
import java.time.ZoneOffset;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class InternationalFixedChronology_ESTest_test24 extends InternationalFixedChronology_ESTest_scaffolding {

    /**
     * The current International Fixed date (taken from the mocked system clock in
     * UTC) falls in a non-leap year, so its year length is the standard 365 days.
     */
    @Test(timeout = 4000)
    public void dateNowInUtc_reportsStandardYearLengthOf365Days() throws Throwable {
        InternationalFixedChronology chronology = new InternationalFixedChronology();
        ZoneId utcZone = ZoneOffset.UTC;

        InternationalFixedDate today = chronology.dateNow(utcZone);

        assertEquals(365, today.lengthOfYear());
    }
}
