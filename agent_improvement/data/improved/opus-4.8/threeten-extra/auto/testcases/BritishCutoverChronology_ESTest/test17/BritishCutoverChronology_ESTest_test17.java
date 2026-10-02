package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.assertNull;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class BritishCutoverChronology_ESTest_test17 extends BritishCutoverChronology_ESTest_scaffolding {

    /**
     * The British Cutover calendar system has no LDML calendar-type identifier,
     * so {@link BritishCutoverChronology#getCalendarType()} is expected to return null.
     */
    @Test(timeout = 4000)
    public void getCalendarType_returnsNull() throws Throwable {
        BritishCutoverChronology chronology = new BritishCutoverChronology();

        String calendarType = chronology.getCalendarType();

        assertNull(calendarType);
    }
}
