package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import java.time.Clock;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockClock;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class InternationalFixedChronology_ESTest_test26 extends InternationalFixedChronology_ESTest_scaffolding {

    /**
     * The mocked clock is fixed to a non-leap year, so the International Fixed
     * date obtained via {@code dateNow(Clock)} should report a year length of
     * 365 days.
     */
    @Test(timeout = 4000)
    public void dateNowFromClock_inNonLeapYear_hasYearLengthOf365Days() throws Throwable {
        InternationalFixedChronology chronology = new InternationalFixedChronology();
        Clock utcClock = MockClock.systemUTC();

        InternationalFixedDate today = chronology.dateNow(utcClock);

        assertEquals(365, today.lengthOfYear());
    }
}
