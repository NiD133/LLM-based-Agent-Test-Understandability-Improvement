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
public class JsonIgnoreProperties_ESTest_test10 extends JsonIgnoreProperties_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test10() throws Throwable {
        LinkedHashSet<String> ignoredProperties = new LinkedHashSet<String>();

        JsonIgnoreProperties.Value value = JsonIgnoreProperties.Value.construct(
                ignoredProperties, true, true, false, true);

        ignoredProperties.remove(value);

        assertTrue(value.getIgnoreUnknown());
        assertTrue(value.getMerge());
        assertTrue(value.getAllowGetters());
        assertFalse(value.getAllowSetters());
    }
}
