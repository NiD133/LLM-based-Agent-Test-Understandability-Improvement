package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Locale;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonFormat_ESTest_test03 extends JsonFormat_ESTest_scaffolding {

    /**
     * Verifies that deriving a new {@link JsonFormat.Value} via {@code withLocale}
     * produces an instance that is no longer {@code equals} to the original default
     * value, while radix-related defaults remain untouched.
     */
    @Test(timeout = 4000)
    public void withLocaleProducesValueNotEqualToDefault() throws Throwable {
        JsonFormat.Value defaultValue = new JsonFormat.Value();

        JsonFormat.Value valueWithGermanLocale = defaultValue.withLocale(Locale.GERMAN);

        // Differing locale means the derived value is not equal to the default one.
        boolean isEqualToDefault = valueWithGermanLocale.equals(defaultValue);
        assertFalse(isEqualToDefault);

        // Setting only the locale leaves the radix at its default (-1 / "not specified").
        assertFalse(valueWithGermanLocale.hasNonDefaultRadix());
        assertEquals(-1, defaultValue.getRadix());
    }
}
