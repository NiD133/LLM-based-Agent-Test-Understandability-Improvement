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
public class BritishCutoverChronology_ESTest_test24 extends BritishCutoverChronology_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void testDateNowWithMaxZoneOffset() throws Throwable {
        BritishCutoverChronology chronology = new BritishCutoverChronology();
        // ZoneOffset.MAX is +18:00, the maximum valid UTC offset
        ZoneOffset maxOffset = ZoneOffset.MAX;
        BritishCutoverDate today = chronology.dateNow((ZoneId) maxOffset);
        assertNotNull(today);
    }
}
