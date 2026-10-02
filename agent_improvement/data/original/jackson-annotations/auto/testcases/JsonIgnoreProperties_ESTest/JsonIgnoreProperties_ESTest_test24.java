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
public class JsonIgnoreProperties_ESTest_test24 extends JsonIgnoreProperties_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test24() throws Throwable {
        JsonIgnoreProperties.Value jsonIgnoreProperties_Value0 = JsonIgnoreProperties.Value.empty();
        Set<String> set0 = jsonIgnoreProperties_Value0.findIgnoredForSerialization();
        JsonIgnoreProperties.Value jsonIgnoreProperties_Value1 = JsonIgnoreProperties.Value.construct(set0, true, true, false, false);
        JsonIgnoreProperties.Value jsonIgnoreProperties_Value2 = jsonIgnoreProperties_Value1.withAllowGetters();
        assertFalse(jsonIgnoreProperties_Value2.getMerge());
        assertFalse(jsonIgnoreProperties_Value2.getAllowSetters());
        assertSame(jsonIgnoreProperties_Value2, jsonIgnoreProperties_Value1);
        assertTrue(jsonIgnoreProperties_Value2.getIgnoreUnknown());
    }
}
