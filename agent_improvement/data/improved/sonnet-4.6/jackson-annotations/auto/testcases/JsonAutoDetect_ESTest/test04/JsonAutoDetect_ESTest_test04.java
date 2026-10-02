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
public class JsonAutoDetect_ESTest_test04 extends JsonAutoDetect_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test04() throws Throwable {
        // Start from the default visibility configuration
        JsonAutoDetect.Value defaultConfig = JsonAutoDetect.Value.DEFAULT;

        // Override only the scalar constructor visibility to ANY; all other visibilities stay at their defaults
        JsonAutoDetect.Value updatedConfig = defaultConfig.withScalarConstructorVisibility(JsonAutoDetect.Visibility.ANY);

        // Non-scalar-constructor visibilities remain unchanged from DEFAULT
        assertEquals(JsonAutoDetect.Visibility.PUBLIC_ONLY, updatedConfig.getCreatorVisibility());
        assertEquals(JsonAutoDetect.Visibility.PUBLIC_ONLY, updatedConfig.getIsGetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.PUBLIC_ONLY, updatedConfig.getGetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.PUBLIC_ONLY, updatedConfig.getFieldVisibility());
        assertEquals(JsonAutoDetect.Visibility.ANY,         updatedConfig.getSetterVisibility());

        // Scalar constructor visibility reflects the overridden value
        assertEquals(JsonAutoDetect.Visibility.ANY, updatedConfig.getScalarConstructorVisibility());
    }
}
