package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.format.TextStyle;
import java.util.Locale;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JulianChronology_ESTest_test13 extends JulianChronology_ESTest_scaffolding {

    /**
     * Verifies the human-readable display name and the machine-readable
     * calendar type reported by the Julian chronology singleton.
     */
    @Test(timeout = 4000)
    public void displayNameAndCalendarTypeReturnJulianIdentifiers() throws Throwable {
        JulianChronology julianChronology = JulianChronology.INSTANCE;

        String displayName = julianChronology.getDisplayName(TextStyle.SHORT, Locale.ENGLISH);

        assertEquals("Julian", displayName);
        assertEquals("julian", julianChronology.getCalendarType());
    }
}
