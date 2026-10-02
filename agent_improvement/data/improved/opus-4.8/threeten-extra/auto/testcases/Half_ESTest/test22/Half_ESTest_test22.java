package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.format.TextStyle;
import java.util.Locale;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Half_ESTest_test22 extends Half_ESTest_scaffolding {

    /**
     * Subtracting an even number of halves leaves the half unchanged, so the
     * display name falls back to the numeric value ("2" for H2) when no textual
     * mapping exists for the given locale.
     */
    @Test(timeout = 4000)
    public void minusEvenHalves_displayNameFallsBackToNumericValue() throws Throwable {
        // -1096 is even, so subtracting it rolls fully around and yields H2 again.
        Half unchangedHalf = Half.H2.minus(-1096L);

        Locale localeWithoutHalfText = new Locale("HalfYears");
        String displayName = unchangedHalf.getDisplayName(TextStyle.NARROW, localeWithoutHalfText);

        assertEquals("2", displayName);
    }
}
