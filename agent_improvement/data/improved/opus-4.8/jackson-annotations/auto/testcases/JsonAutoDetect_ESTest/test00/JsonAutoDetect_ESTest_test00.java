package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.lang.reflect.Member;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonAutoDetect_ESTest_test00 extends JsonAutoDetect_ESTest_scaffolding {

    /**
     * Verifies that {@link JsonAutoDetect.Value#withOverrides} merges two Value
     * instances correctly: overrides whose visibility is {@code DEFAULT} keep the
     * base value, while non-DEFAULT overrides replace it.
     */
    @Test(timeout = 4000)
    public void withOverridesMergesNonDefaultVisibilityOnly() throws Throwable {
        JsonAutoDetect.Visibility none = JsonAutoDetect.Visibility.NONE;
        JsonAutoDetect.Visibility publicOnly = JsonAutoDetect.Visibility.PUBLIC_ONLY;

        // Base value: every accessor is NONE except the creator, which is PUBLIC_ONLY.
        // Argument order: fields, getters, isGetters, setters, creators, scalarConstructors.
        JsonAutoDetect.Value baseValue = JsonAutoDetect.Value.construct(
                none, none, none, none, publicOnly, none);

        // Override value: only the scalar-constructor visibility is set (to PUBLIC_ONLY);
        // all other accessors are left as DEFAULT and therefore won't override the base.
        JsonAutoDetect.Value scalarConstructorOverride = JsonAutoDetect.Value.construct(
                PropertyAccessor.SCALAR_CONSTRUCTOR, publicOnly);

        JsonAutoDetect.Value mergedValue = baseValue.withOverrides(scalarConstructorOverride);

        // Accessors left as DEFAULT in the override keep the base value (NONE).
        assertEquals(JsonAutoDetect.Visibility.NONE, mergedValue.getSetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.NONE, mergedValue.getGetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.NONE, mergedValue.getFieldVisibility());
        // Creator visibility was already PUBLIC_ONLY in the base and is preserved.
        assertEquals(JsonAutoDetect.Visibility.PUBLIC_ONLY, mergedValue.getCreatorVisibility());

        // Scalar-constructor visibility: NONE in the base, PUBLIC_ONLY after the override.
        assertEquals(JsonAutoDetect.Visibility.NONE, baseValue.getScalarConstructorVisibility());
        assertEquals(JsonAutoDetect.Visibility.PUBLIC_ONLY, mergedValue.getScalarConstructorVisibility());

        // Unspecified accessors in the single-accessor factory default to DEFAULT.
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, scalarConstructorOverride.getIsGetterVisibility());

        // The merged value differs from the base, so they are not equal.
        assertFalse(mergedValue.equals((Object) baseValue));
    }
}
