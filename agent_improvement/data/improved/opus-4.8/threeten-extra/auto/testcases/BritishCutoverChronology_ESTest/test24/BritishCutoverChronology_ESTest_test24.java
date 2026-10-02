package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.assertNotNull;
import java.time.ZoneId;
import java.time.ZoneOffset;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class BritishCutoverChronology_ESTest_test24 extends BritishCutoverChronology_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void dateNowWithZoneReturnsCurrentDate() throws Throwable {
        BritishCutoverChronology chronology = new BritishCutoverChronology();
        ZoneId zone = ZoneOffset.MAX;

        BritishCutoverDate currentDate = chronology.dateNow(zone);

        assertNotNull(currentDate);
    }
}
