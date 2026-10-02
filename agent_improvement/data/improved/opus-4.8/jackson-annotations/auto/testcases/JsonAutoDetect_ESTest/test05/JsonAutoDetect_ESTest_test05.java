package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonAutoDetect_ESTest_test05 extends JsonAutoDetect_ESTest_scaffolding {

    /**
     * Starting from the DEFAULT Value, overriding only the is-getter visibility
     * should change that single accessor while every other accessor keeps the
     * DEFAULT visibility it had before.
     */
    @Test(timeout = 4000)
    public void withIsGetterVisibility_overridesOnlyIsGetter() throws Throwable {
        JsonAutoDetect.Value defaultValue = JsonAutoDetect.Value.DEFAULT;

        JsonAutoDetect.Value overridden =
                defaultValue.withIsGetterVisibility(JsonAutoDetect.Visibility.PROTECTED_AND_PUBLIC);

        // The overridden accessor now uses the requested visibility.
        assertEquals(JsonAutoDetect.Visibility.PROTECTED_AND_PUBLIC, overridden.getIsGetterVisibility());

        // All other accessors retain the DEFAULT visibilities.
        assertEquals(JsonAutoDetect.Visibility.PUBLIC_ONLY, overridden.getFieldVisibility());
        assertEquals(JsonAutoDetect.Visibility.PUBLIC_ONLY, overridden.getGetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.ANY, overridden.getSetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.PUBLIC_ONLY, overridden.getCreatorVisibility());
        assertEquals(JsonAutoDetect.Visibility.NON_PRIVATE, overridden.getScalarConstructorVisibility());
    }
}
