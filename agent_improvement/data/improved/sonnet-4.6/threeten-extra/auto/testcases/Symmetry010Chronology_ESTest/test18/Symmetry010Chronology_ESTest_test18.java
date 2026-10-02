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

    @Test(timeout = 4000)
    public void getDisplayName_returnsChronologyId_forFullStyleAndTaiwanLocale() throws Throwable {
        // getDisplayName falls back to the chronology ID "Sym010" for any locale,
        // since Symmetry010 has no locale-specific display name registered.
        String displayName = Symmetry010Chronology.INSTANCE.getDisplayName(TextStyle.FULL, Locale.TAIWAN);
        assertEquals("Sym010", displayName);
    }
}
