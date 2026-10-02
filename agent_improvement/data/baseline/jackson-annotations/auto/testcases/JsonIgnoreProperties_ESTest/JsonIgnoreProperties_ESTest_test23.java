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
public class JsonIgnoreProperties_ESTest_test23 extends JsonIgnoreProperties_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test23() throws Throwable {
        JsonIgnoreProperties.Value jsonIgnoreProperties_Value0 = JsonIgnoreProperties.Value.empty();
        Set<String> set0 = jsonIgnoreProperties_Value0.findIgnoredForSerialization();
        JsonIgnoreProperties.Value jsonIgnoreProperties_Value1 = JsonIgnoreProperties.Value.construct(set0, true, true, false, false);
        JsonIgnoreProperties.Value jsonIgnoreProperties_Value2 = jsonIgnoreProperties_Value1.withoutAllowGetters();
        assertNotSame(jsonIgnoreProperties_Value2, jsonIgnoreProperties_Value1);
        assertFalse(jsonIgnoreProperties_Value1.getMerge());
        assertTrue(jsonIgnoreProperties_Value2.getIgnoreUnknown());
        assertFalse(jsonIgnoreProperties_Value1.getAllowSetters());
        assertTrue(jsonIgnoreProperties_Value1.getIgnoreUnknown());
        assertFalse(jsonIgnoreProperties_Value2.getAllowGetters());
        assertFalse(jsonIgnoreProperties_Value2.getAllowSetters());
        assertFalse(jsonIgnoreProperties_Value2.getMerge());
    }
}
