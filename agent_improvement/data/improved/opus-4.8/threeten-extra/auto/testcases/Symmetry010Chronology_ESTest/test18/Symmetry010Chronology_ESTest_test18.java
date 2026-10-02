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
public class Symmetry010Chronology_ESTest_test18 extends Symmetry010Chronology_ESTest_scaffolding {

    /**
     * The Symmetry010 chronology has no localized display names, so
     * {@code getDisplayName} falls back to the chronology ID "Sym010"
     * regardless of the requested text style or locale.
     */
    @Test(timeout = 4000)
    public void getDisplayNameReturnsChronologyId() throws Throwable {
        Symmetry010Chronology chronology = new Symmetry010Chronology();

        String displayName = chronology.getDisplayName(TextStyle.FULL, Locale.TAIWAN);

        assertEquals("Sym010", displayName);
    }
}
