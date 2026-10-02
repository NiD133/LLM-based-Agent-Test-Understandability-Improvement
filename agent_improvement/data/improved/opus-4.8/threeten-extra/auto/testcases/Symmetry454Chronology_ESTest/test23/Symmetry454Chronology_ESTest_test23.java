package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.assertNull;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Symmetry454Chronology_ESTest_test23 extends Symmetry454Chronology_ESTest_scaffolding {

    /**
     * The Symmetry454 calendar has no Unicode LDML calendar-type identifier,
     * so {@link Symmetry454Chronology#getCalendarType()} is expected to return null.
     */
    @Test(timeout = 4000)
    public void getCalendarType_returnsNull() throws Throwable {
        Symmetry454Chronology chronology = Symmetry454Chronology.INSTANCE;

        String calendarType = chronology.getCalendarType();

        assertNull(calendarType);
    }
}
