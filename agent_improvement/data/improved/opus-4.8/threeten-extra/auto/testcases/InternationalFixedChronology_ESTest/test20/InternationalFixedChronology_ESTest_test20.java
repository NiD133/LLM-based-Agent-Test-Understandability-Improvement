package org.threeten.extra.chrono;

import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class InternationalFixedChronology_ESTest_test20 extends InternationalFixedChronology_ESTest_scaffolding {

    /**
     * The International Fixed calendar system has no Unicode LDML calendar type,
     * so {@link InternationalFixedChronology#getCalendarType()} returns null.
     */
    @Test(timeout = 4000)
    public void getCalendarType_returnsNull() throws Throwable {
        InternationalFixedChronology chronology = new InternationalFixedChronology();

        String calendarType = chronology.getCalendarType();

        assertNull(calendarType);
    }
}
