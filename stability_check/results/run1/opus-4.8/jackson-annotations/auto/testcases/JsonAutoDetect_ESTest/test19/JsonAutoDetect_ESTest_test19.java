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
     * When constructing a Value for PropertyAccessor.NONE, the supplied
     * visibility (PUBLIC_ONLY) is applied to no accessor, so every accessor
     * keeps its DEFAULT visibility.
     */
    @Test(timeout = 4000)
    public void constructForNoneAccessorLeavesAllVisibilitiesDefault() throws Throwable {
        JsonAutoDetect.Value value = JsonAutoDetect.Value.construct(
                PropertyAccessor.NONE, JsonAutoDetect.Visibility.PUBLIC_ONLY);

        JsonAutoDetect.Visibility expected = JsonAutoDetect.Visibility.DEFAULT;
        assertEquals(expected, value.getGetterVisibility());
        assertEquals(expected, value.getIsGetterVisibility());
        assertEquals(expected, value.getCreatorVisibility());
        assertEquals(expected, value.getFieldVisibility());
        assertEquals(expected, value.getSetterVisibility());
        assertEquals(expected, value.getScalarConstructorVisibility());
    }
}
