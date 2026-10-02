package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class BritishCutoverChronology_ESTest_test17 extends BritishCutoverChronology_ESTest_scaffolding {

    /**
     * The BritishCutover calendar system has no LDML identifier, so getCalendarType()
     * is specified to return null (unlike, e.g., ISO which returns "iso8601").
     */
    @Test(timeout = 4000)
    public void test_getCalendarType_returnsNull() throws Throwable {
        BritishCutoverChronology chronology = new BritishCutoverChronology();
        String calendarType = chronology.getCalendarType();
        assertNull("BritishCutover has no LDML calendar type identifier, so getCalendarType() must return null", calendarType);
    }
}
