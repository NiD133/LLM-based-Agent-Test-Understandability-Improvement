package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonAutoDetect_ESTest_test19 extends JsonAutoDetect_ESTest_scaffolding {

    /**
     * Value.construct(PropertyAccessor, Visibility) applies the given visibility only
     * to the accessors selected by the PropertyAccessor. When the accessor is NONE,
     * no accessor is targeted, so every accessor visibility stays at its DEFAULT value
     * regardless of the visibility argument passed in.
     */
    @Test(timeout = 4000)
    public void constructWithNoneAccessorLeavesAllVisibilitiesDefault() throws Throwable {
        JsonAutoDetect.Value value =
                JsonAutoDetect.Value.construct(PropertyAccessor.NONE, JsonAutoDetect.Visibility.PUBLIC_ONLY);

        assertEquals(JsonAutoDetect.Visibility.DEFAULT, value.getGetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, value.getIsGetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, value.getCreatorVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, value.getFieldVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, value.getSetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, value.getScalarConstructorVisibility());
    }
}
