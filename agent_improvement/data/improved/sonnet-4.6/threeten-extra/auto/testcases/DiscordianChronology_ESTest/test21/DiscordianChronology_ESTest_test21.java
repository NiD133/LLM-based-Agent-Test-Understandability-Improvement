package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.ZoneId;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DiscordianChronology_ESTest_test21 extends DiscordianChronology_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void dateNow_withNullZoneId_throwsNullPointerException() throws Throwable {
        DiscordianChronology chronology = DiscordianChronology.INSTANCE;
        try {
            chronology.dateNow((ZoneId) null);
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException e) {
            verifyException("java.util.Objects", e);
        }
    }
}
