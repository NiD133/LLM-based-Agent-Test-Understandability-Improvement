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

    @Test(timeout = 4000)
    public void test22() throws Throwable {
        // Subtracting an even number (-1096) from H2 leaves the half unchanged,
        // because -1096 % 2 == 0, so minus() effectively adds 0 steps.
        Half secondHalf = Half.H2;
        Half resultHalf = secondHalf.minus(-1096L);

        Locale customLocale = new Locale("HalfYears");
        String displayName = resultHalf.getDisplayName(TextStyle.NARROW, customLocale);

        assertEquals("2", displayName);
    }
}
