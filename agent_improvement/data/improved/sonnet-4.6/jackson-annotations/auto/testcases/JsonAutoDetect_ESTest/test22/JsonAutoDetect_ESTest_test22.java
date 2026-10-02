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
public class JsonAutoDetect_ESTest_test22 extends JsonAutoDetect_ESTest_scaffolding {

    /**
     * Verifies that constructing a Value for the FIELD accessor with ANY visibility
     * sets field visibility to ANY while leaving all other accessor visibilities at DEFAULT.
     */
    @Test(timeout = 4000)
    public void test22() throws Throwable {
        JsonAutoDetect.Visibility anyVisibility = JsonAutoDetect.Visibility.ANY;
        PropertyAccessor fieldAccessor = PropertyAccessor.FIELD;

        JsonAutoDetect.Value value = JsonAutoDetect.Value.construct(fieldAccessor, anyVisibility);

        assertEquals(JsonAutoDetect.Visibility.ANY,     value.getFieldVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, value.getSetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, value.getScalarConstructorVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, value.getCreatorVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, value.getIsGetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, value.getGetterVisibility());
    }
}
