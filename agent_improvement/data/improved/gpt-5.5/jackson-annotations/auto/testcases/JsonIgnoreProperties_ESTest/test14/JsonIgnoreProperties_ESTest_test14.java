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
public class JsonIgnoreProperties_ESTest_test14 extends JsonIgnoreProperties_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test14() throws Throwable {
        JsonIgnoreProperties.Value valueFromMissingAnnotation = JsonIgnoreProperties.Value.from((JsonIgnoreProperties) null);

        JsonIgnoreProperties.Value resolvedDefaultValue =
                (JsonIgnoreProperties.Value) valueFromMissingAnnotation.readResolve();

        assertFalse(resolvedDefaultValue.getAllowGetters());
    }
}
