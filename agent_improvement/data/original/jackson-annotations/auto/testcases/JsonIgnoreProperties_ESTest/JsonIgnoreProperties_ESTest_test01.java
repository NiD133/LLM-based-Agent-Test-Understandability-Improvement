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
public class JsonIgnoreProperties_ESTest_test01 extends JsonIgnoreProperties_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test01() throws Throwable {
        String[] stringArray0 = new String[6];
        JsonIgnoreProperties.Value jsonIgnoreProperties_Value0 = JsonIgnoreProperties.Value.forIgnoredProperties(stringArray0);
        JsonIgnoreProperties.Value jsonIgnoreProperties_Value1 = jsonIgnoreProperties_Value0.withIgnoreUnknown();
        JsonIgnoreProperties.Value jsonIgnoreProperties_Value2 = JsonIgnoreProperties.Value.merge(jsonIgnoreProperties_Value0, jsonIgnoreProperties_Value1);
        assertTrue(jsonIgnoreProperties_Value2.equals((Object) jsonIgnoreProperties_Value1));
        assertFalse(jsonIgnoreProperties_Value2.getAllowGetters());
        assertTrue(jsonIgnoreProperties_Value2.getIgnoreUnknown());
        assertTrue(jsonIgnoreProperties_Value0.getMerge());
        assertFalse(jsonIgnoreProperties_Value2.getAllowSetters());
        assertNotSame(jsonIgnoreProperties_Value2, jsonIgnoreProperties_Value1);
        assertFalse(jsonIgnoreProperties_Value0.getAllowSetters());
    }
}
