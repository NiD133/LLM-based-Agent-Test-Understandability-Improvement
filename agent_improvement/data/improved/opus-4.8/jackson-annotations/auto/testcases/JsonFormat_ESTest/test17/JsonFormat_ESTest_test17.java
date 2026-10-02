package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Locale;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonFormat_ESTest_test17 extends JsonFormat_ESTest_scaffolding {

    /**
     * Verifies that withLocale() records the supplied locale (so hasLocale()
     * becomes true) while leaving the radix at its default value (-1).
     */
    @Test(timeout = 4000)
    public void withLocale_setsLocaleAndKeepsDefaultRadix() throws Throwable {
        JsonFormat.Value emptyValue = JsonFormat.Value.empty();

        JsonFormat.Value valueWithLocale = emptyValue.withLocale(Locale.GERMAN);

        assertTrue("locale should be present after withLocale()", valueWithLocale.hasLocale());
        assertEquals("radix should remain at default", -1, valueWithLocale.getRadix());
    }
}
