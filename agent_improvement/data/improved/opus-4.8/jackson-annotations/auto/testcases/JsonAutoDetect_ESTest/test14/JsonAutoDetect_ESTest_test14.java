package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonAutoDetect_ESTest_test14 extends JsonAutoDetect_ESTest_scaffolding {

    /**
     * Applying the "no overrides" Value as overrides onto itself should leave every
     * visibility setting untouched, so the getter visibility stays at DEFAULT.
     */
    @Test(timeout = 4000)
    public void withOverridesOfNoOverridesKeepsGetterVisibilityDefault() throws Throwable {
        JsonAutoDetect.Value noOverrides = JsonAutoDetect.Value.NO_OVERRIDES;

        JsonAutoDetect.Value merged = noOverrides.withOverrides(noOverrides);

        assertEquals(JsonAutoDetect.Visibility.DEFAULT, merged.getGetterVisibility());
    }
}
