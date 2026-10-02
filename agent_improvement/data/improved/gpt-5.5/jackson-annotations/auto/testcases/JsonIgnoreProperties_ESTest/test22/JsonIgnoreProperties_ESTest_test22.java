package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import java.util.LinkedHashSet;
import java.util.Set;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonIgnoreProperties_ESTest_test22 extends JsonIgnoreProperties_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test22() throws Throwable {
        // Arrange: a null annotation resolves to the default Value instance.
        JsonIgnoreProperties.Value defaultValue = JsonIgnoreProperties.Value.from((JsonIgnoreProperties) null);

        // Act: disabling allowed getters on the default value keeps allow-getters false.
        JsonIgnoreProperties.Value valueWithoutAllowedGetters = defaultValue.withoutAllowGetters();

        // Assert
        assertFalse(valueWithoutAllowedGetters.getAllowGetters());
    }
}
