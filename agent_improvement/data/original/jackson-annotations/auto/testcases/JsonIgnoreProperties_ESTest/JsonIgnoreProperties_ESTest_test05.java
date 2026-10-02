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
public class JsonIgnoreProperties_ESTest_test05 extends JsonIgnoreProperties_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test05() throws Throwable {
        JsonIgnoreProperties.Value jsonIgnoreProperties_Value0 = JsonIgnoreProperties.Value.empty();
        JsonIgnoreProperties.Value jsonIgnoreProperties_Value1 = jsonIgnoreProperties_Value0.withAllowSetters();
        boolean boolean0 = jsonIgnoreProperties_Value0.equals(jsonIgnoreProperties_Value1);
        assertFalse(jsonIgnoreProperties_Value1.getIgnoreUnknown());
        assertTrue(jsonIgnoreProperties_Value1.getMerge());
        assertFalse(jsonIgnoreProperties_Value1.getAllowGetters());
        assertFalse(jsonIgnoreProperties_Value1.equals((Object) jsonIgnoreProperties_Value0));
        assertFalse(boolean0);
    }
}
