package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockClock;
import org.evosuite.runtime.mock.java.time.MockZonedDateTime;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class PaxChronology_ESTest_test22 extends PaxChronology_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test22() throws Throwable {
        PaxChronology chronology = PaxChronology.INSTANCE;

        String calendarType = chronology.getCalendarType();

        assertEquals("pax", calendarType);
    }
}
