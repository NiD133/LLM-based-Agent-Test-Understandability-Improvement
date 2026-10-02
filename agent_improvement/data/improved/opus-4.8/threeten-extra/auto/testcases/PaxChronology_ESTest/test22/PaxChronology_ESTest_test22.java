package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class PaxChronology_ESTest_test22 extends PaxChronology_ESTest_scaffolding {

    /**
     * The Pax chronology's calendar type should be the lowercase "pax",
     * as documented on {@link PaxChronology#getCalendarType()}.
     */
    @Test(timeout = 4000)
    public void getCalendarType_returnsPax() throws Throwable {
        PaxChronology paxChronology = PaxChronology.INSTANCE;

        String calendarType = paxChronology.getCalendarType();

        assertEquals("pax", calendarType);
    }
}
