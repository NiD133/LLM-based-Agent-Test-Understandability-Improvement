package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Symmetry454Chronology_ESTest_test23 extends Symmetry454Chronology_ESTest_scaffolding {

    /**
     * Symmetry454 has no LDML calendar type identifier, so getCalendarType() must return null.
     */
    @Test(timeout = 4000)
    public void test_getCalendarType_returnsNull() throws Throwable {
        String calendarType = Symmetry454Chronology.INSTANCE.getCalendarType();
        assertNull(calendarType);
    }
}
