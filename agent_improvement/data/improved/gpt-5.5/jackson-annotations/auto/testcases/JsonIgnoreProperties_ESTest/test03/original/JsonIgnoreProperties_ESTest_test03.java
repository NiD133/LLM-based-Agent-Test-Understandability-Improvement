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
public class JsonIgnoreProperties_ESTest_test03 extends JsonIgnoreProperties_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test03() throws Throwable {
        JsonIgnoreProperties.Value jsonIgnoreProperties_Value0 = JsonIgnoreProperties.Value.from((JsonIgnoreProperties) null);
        LinkedHashSet<String> linkedHashSet0 = new LinkedHashSet<String>();
        JsonIgnoreProperties.Value jsonIgnoreProperties_Value1 = JsonIgnoreProperties.Value.construct(linkedHashSet0, false, true, true, true);
        JsonIgnoreProperties.Value jsonIgnoreProperties_Value2 = jsonIgnoreProperties_Value0.withOverrides(jsonIgnoreProperties_Value1);
        assertTrue(jsonIgnoreProperties_Value2.getAllowGetters());
        assertNotSame(jsonIgnoreProperties_Value2, jsonIgnoreProperties_Value1);
        assertTrue(jsonIgnoreProperties_Value2.equals((Object) jsonIgnoreProperties_Value1));
        assertFalse(jsonIgnoreProperties_Value2.getIgnoreUnknown());
        assertTrue(jsonIgnoreProperties_Value1.getAllowSetters());
    }
}
