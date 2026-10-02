package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonAutoDetect_ESTest_test11 extends JsonAutoDetect_ESTest_scaffolding {

    /**
     * Builds a Value that overrides only the CREATOR visibility (to NONE) and
     * leaves every other accessor at DEFAULT, then verifies that round-tripping
     * it through readResolve() preserves those exact per-accessor settings.
     * Because this combination matches no predefined constant, readResolve()
     * returns an equivalent Value rather than a shared singleton.
     */
    @Test(timeout = 4000)
    public void creatorOnlyValueSurvivesReadResolve() throws Throwable {
        JsonAutoDetect.Value creatorOnlyValue =
                JsonAutoDetect.Value.construct(PropertyAccessor.CREATOR, JsonAutoDetect.Visibility.NONE);

        JsonAutoDetect.Value resolvedValue = (JsonAutoDetect.Value) creatorOnlyValue.readResolve();

        assertNotNull(resolvedValue);
        // Only the creator visibility carries the explicit override...
        assertEquals(JsonAutoDetect.Visibility.NONE, resolvedValue.getCreatorVisibility());
        // ...every other accessor stays at DEFAULT.
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, resolvedValue.getScalarConstructorVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, resolvedValue.getFieldVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, resolvedValue.getSetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, resolvedValue.getIsGetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, resolvedValue.getGetterVisibility());
    }
}
