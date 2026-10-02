package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonAutoDetect_ESTest_test12 extends JsonAutoDetect_ESTest_scaffolding {

    /**
     * The NO_OVERRIDES value sets every accessor visibility to DEFAULT.
     * Its readResolve() (used during JDK deserialization) should map back to
     * a canonical instance whose setter visibility is still DEFAULT.
     */
    @Test(timeout = 4000)
    public void readResolveOfNoOverridesKeepsDefaultSetterVisibility() throws Throwable {
        JsonAutoDetect.Value noOverrides = JsonAutoDetect.Value.NO_OVERRIDES;

        JsonAutoDetect.Value resolved = (JsonAutoDetect.Value) noOverrides.readResolve();

        assertEquals(JsonAutoDetect.Visibility.DEFAULT, resolved.getSetterVisibility());
    }
}
