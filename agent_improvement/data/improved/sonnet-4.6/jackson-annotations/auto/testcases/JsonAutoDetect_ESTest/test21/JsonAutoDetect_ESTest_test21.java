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
public class JsonAutoDetect_ESTest_test21 extends JsonAutoDetect_ESTest_scaffolding {

    /**
     * Verifies that constructing a Value with only the SETTER accessor set to NON_PRIVATE
     * leaves all other accessor visibilities at their DEFAULT values.
     */
    @Test(timeout = 4000)
    public void test21() throws Throwable {
        JsonAutoDetect.Visibility nonPrivateVisibility = JsonAutoDetect.Visibility.NON_PRIVATE;
        PropertyAccessor setterAccessor = PropertyAccessor.SETTER;

        // Construct a Value that only overrides the setter visibility
        JsonAutoDetect.Value valueWithSetterVisibility = JsonAutoDetect.Value.construct(setterAccessor, nonPrivateVisibility);

        // Only setter visibility should be NON_PRIVATE; all others remain DEFAULT
        assertEquals(JsonAutoDetect.Visibility.NON_PRIVATE, valueWithSetterVisibility.getSetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, valueWithSetterVisibility.getGetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, valueWithSetterVisibility.getIsGetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, valueWithSetterVisibility.getFieldVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, valueWithSetterVisibility.getScalarConstructorVisibility());
    }
}
