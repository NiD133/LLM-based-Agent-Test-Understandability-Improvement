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

    @Test(timeout = 4000)
    public void test22() throws Throwable {
        JsonAutoDetect.Visibility requestedFieldVisibility = JsonAutoDetect.Visibility.ANY;
        PropertyAccessor selectedAccessor = PropertyAccessor.FIELD;

        JsonAutoDetect.Value visibilityOverrides =
                JsonAutoDetect.Value.construct(selectedAccessor, requestedFieldVisibility);

        assertEquals("FIELD accessor should receive the requested visibility",
                JsonAutoDetect.Visibility.ANY, visibilityOverrides.getFieldVisibility());
        assertEquals("SETTER accessor should keep the default visibility",
                JsonAutoDetect.Visibility.DEFAULT, visibilityOverrides.getSetterVisibility());
        assertEquals("SCALAR_CONSTRUCTOR accessor should keep the default visibility",
                JsonAutoDetect.Visibility.DEFAULT, visibilityOverrides.getScalarConstructorVisibility());
        assertEquals("CREATOR accessor should keep the default visibility",
                JsonAutoDetect.Visibility.DEFAULT, visibilityOverrides.getCreatorVisibility());
        assertEquals("IS_GETTER accessor should keep the default visibility",
                JsonAutoDetect.Visibility.DEFAULT, visibilityOverrides.getIsGetterVisibility());
        assertEquals("GETTER accessor should keep the default visibility",
                JsonAutoDetect.Visibility.DEFAULT, visibilityOverrides.getGetterVisibility());
    }
}
