package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.Clock;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockClock;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class PaxChronology_ESTest_test19 extends PaxChronology_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test19() throws Throwable {
        PaxChronology chronology = new PaxChronology();
        Clock defaultZoneClock = MockClock.systemDefaultZone();

        PaxDate currentDate = chronology.INSTANCE.dateNow(defaultZoneClock);

        assertEquals(PaxEra.CE, currentDate.getEra());
    }
}
