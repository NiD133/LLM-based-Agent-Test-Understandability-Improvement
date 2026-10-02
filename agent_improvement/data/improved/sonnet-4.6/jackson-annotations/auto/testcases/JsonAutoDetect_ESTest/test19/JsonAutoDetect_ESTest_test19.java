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
public class JsonAutoDetect_ESTest_test19 extends JsonAutoDetect_ESTest_scaffolding {

    /**
     * When constructing a JsonAutoDetect.Value using PropertyAccessor.NONE, no accessor
     * category is targeted, so all visibility settings should remain DEFAULT (unchanged).
     */
    @Test(timeout = 4000)
    public void test_constructWithAccessorNone_leavesAllVisibilitiesAsDefault() throws Throwable {
        // NONE means no accessor category is selected, so no visibility is modified
        PropertyAccessor noAccessorCategory = PropertyAccessor.NONE;
        JsonAutoDetect.Visibility publicOnly = JsonAutoDetect.Visibility.PUBLIC_ONLY;

        JsonAutoDetect.Value value = JsonAutoDetect.Value.construct(noAccessorCategory, publicOnly);

        // All visibility settings must remain DEFAULT because PropertyAccessor.NONE
        // does not map to any specific accessor type in the construct() switch statement
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, value.getGetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, value.getIsGetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, value.getCreatorVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, value.getFieldVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, value.getSetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, value.getScalarConstructorVisibility());
    }
}
