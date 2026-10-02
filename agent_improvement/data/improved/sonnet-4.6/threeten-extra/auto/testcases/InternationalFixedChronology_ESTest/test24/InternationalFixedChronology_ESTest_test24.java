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
     * Verifies that the current date obtained via UTC zone has a standard (non-leap) year length of 365.
     * The EvoSuite mock fixes the clock so that the queried date falls in a non-leap year.
     */
    @Test(timeout = 4000)
    public void test24() throws Throwable {
        InternationalFixedChronology chronology = new InternationalFixedChronology();
        ZoneOffset utc = ZoneOffset.UTC;
        InternationalFixedDate today = chronology.dateNow((ZoneId) utc);
        assertEquals(365, today.lengthOfYear());
    }
}
