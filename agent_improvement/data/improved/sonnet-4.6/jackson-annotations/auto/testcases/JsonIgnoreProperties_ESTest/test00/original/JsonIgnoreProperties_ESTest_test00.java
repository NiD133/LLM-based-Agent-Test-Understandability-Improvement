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
public class JsonIgnoreProperties_ESTest_test00 extends JsonIgnoreProperties_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test00() throws Throwable {
        JsonIgnoreProperties.Value[] jsonIgnoreProperties_ValueArray0 = new JsonIgnoreProperties.Value[6];
        JsonIgnoreProperties.Value jsonIgnoreProperties_Value0 = JsonIgnoreProperties.Value.empty();
        Set<String> set0 = jsonIgnoreProperties_Value0.getIgnored();
        JsonIgnoreProperties.Value jsonIgnoreProperties_Value1 = new JsonIgnoreProperties.Value(set0, true, true, true, true);
        String[] stringArray0 = new String[8];
        JsonIgnoreProperties.Value jsonIgnoreProperties_Value2 = JsonIgnoreProperties.Value.forIgnoredProperties(stringArray0);
        assertFalse(jsonIgnoreProperties_Value2.getIgnoreUnknown());
        assertTrue(jsonIgnoreProperties_Value2.getMerge());
        assertFalse(jsonIgnoreProperties_Value2.getAllowSetters());
        jsonIgnoreProperties_ValueArray0[3] = jsonIgnoreProperties_Value2;
        jsonIgnoreProperties_ValueArray0[4] = jsonIgnoreProperties_Value1;
        JsonIgnoreProperties.Value jsonIgnoreProperties_Value3 = JsonIgnoreProperties.Value.mergeAll(jsonIgnoreProperties_ValueArray0);
        assertTrue(jsonIgnoreProperties_Value3.getAllowGetters());
        assertTrue(jsonIgnoreProperties_Value3.getAllowSetters());
        assertTrue(jsonIgnoreProperties_Value3.getIgnoreUnknown());
        assertNotNull(jsonIgnoreProperties_Value3);
        assertFalse(jsonIgnoreProperties_Value3.equals((Object) jsonIgnoreProperties_Value1));
        assertNotSame(jsonIgnoreProperties_Value3, jsonIgnoreProperties_Value1);
    }
}
