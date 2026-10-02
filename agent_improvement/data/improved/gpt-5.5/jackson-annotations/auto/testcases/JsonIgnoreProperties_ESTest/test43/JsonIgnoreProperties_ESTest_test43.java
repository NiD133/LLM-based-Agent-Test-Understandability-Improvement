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
public class JsonIgnoreProperties_ESTest_test43 extends JsonIgnoreProperties_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test43() throws Throwable {
        LinkedHashSet<String> ignoredProperties = new LinkedHashSet<String>();

        JsonIgnoreProperties.Value value = JsonIgnoreProperties.Value.forIgnoredProperties((Set<String>) ignoredProperties);

        assertFalse("Empty ignored-property set should not allow setters", value.getAllowSetters());
        assertFalse("Empty ignored-property set should not ignore unknown properties", value.getIgnoreUnknown());
        assertFalse("Empty ignored-property set should not allow getters", value.getAllowGetters());
        assertTrue("Values created from ignored properties should keep merge enabled", value.getMerge());
    }
}
