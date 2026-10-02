package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class InternationalFixedChronology_ESTest_test20 extends InternationalFixedChronology_ESTest_scaffolding {

    /**
     * The International Fixed Calendar has no LDML identifier, so getCalendarType() must return null.
     */
    @Test(timeout = 4000)
    public void test_getCalendarType_returnsNullBecauseNoLdmlIdentifierIsDefined() throws Throwable {
        InternationalFixedChronology chronology = new InternationalFixedChronology();
        String calendarType = chronology.getCalendarType();
        assertNull(calendarType);
    }
}
