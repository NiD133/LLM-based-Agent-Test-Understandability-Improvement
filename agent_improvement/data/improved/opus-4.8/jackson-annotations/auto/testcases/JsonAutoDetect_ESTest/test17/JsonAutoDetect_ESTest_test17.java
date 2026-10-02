package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonAutoDetect_ESTest_test17 extends JsonAutoDetect_ESTest_scaffolding {

    /**
     * Merging the DEFAULT visibility settings with themselves should leave the
     * configuration unchanged, so getter visibility stays at its default of
     * PUBLIC_ONLY.
     */
    @Test(timeout = 4000)
    public void mergingDefaultWithItselfKeepsPublicOnlyGetterVisibility() throws Throwable {
        JsonAutoDetect.Value defaultSettings = JsonAutoDetect.Value.DEFAULT;

        JsonAutoDetect.Value mergedSettings =
                JsonAutoDetect.Value.merge(defaultSettings, defaultSettings);

        assertEquals(JsonAutoDetect.Visibility.PUBLIC_ONLY,
                mergedSettings.getGetterVisibility());
    }
}
