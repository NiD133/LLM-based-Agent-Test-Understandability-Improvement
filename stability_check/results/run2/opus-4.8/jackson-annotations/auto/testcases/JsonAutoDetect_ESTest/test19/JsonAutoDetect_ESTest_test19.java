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
     * Constructing a Value for accessor NONE leaves every accessor's visibility
     * untouched: the supplied PUBLIC_ONLY level is ignored and all visibilities
     * stay at their DEFAULT value.
     */
    @Test(timeout = 4000)
    public void constructForNoneAccessorLeavesAllVisibilitiesDefault() throws Throwable {
        JsonAutoDetect.Value value = JsonAutoDetect.Value.construct(
                PropertyAccessor.NONE, JsonAutoDetect.Visibility.PUBLIC_ONLY);

        assertEquals(JsonAutoDetect.Visibility.DEFAULT, value.getGetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, value.getIsGetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, value.getCreatorVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, value.getFieldVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, value.getSetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, value.getScalarConstructorVisibility());
    }
}
