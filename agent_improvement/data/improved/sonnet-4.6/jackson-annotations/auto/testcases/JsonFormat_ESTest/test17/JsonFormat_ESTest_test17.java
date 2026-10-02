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
     * Verifies that applying a locale to an empty JsonFormat.Value correctly marks
     * the locale as set, while the radix remains at its default value (-1).
     */
    @Test(timeout = 4000)
    public void test17() throws Throwable {
        JsonFormat.Value emptyValue = JsonFormat.Value.empty();
        JsonFormat.Value valueWithGermanLocale = emptyValue.withLocale(Locale.GERMAN);

        boolean localeIsSet = valueWithGermanLocale.hasLocale();

        assertTrue("Expected hasLocale() to return true after setting Locale.GERMAN", localeIsSet);
        assertEquals("Expected radix to remain at DEFAULT_RADIX (-1) when no radix is configured",
                -1, valueWithGermanLocale.getRadix());
    }
}
