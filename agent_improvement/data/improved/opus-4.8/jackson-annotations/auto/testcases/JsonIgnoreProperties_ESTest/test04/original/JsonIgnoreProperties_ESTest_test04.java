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
public class JsonIgnoreProperties_ESTest_test04 extends JsonIgnoreProperties_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test04() throws Throwable {
        JsonIgnoreProperties jsonIgnoreProperties0 = mock(JsonIgnoreProperties.class, CALLS_REAL_METHODS);
        doReturn(false).when(jsonIgnoreProperties0).allowGetters();
        doReturn(false).when(jsonIgnoreProperties0).allowSetters();
        doReturn(false).when(jsonIgnoreProperties0).ignoreUnknown();
        doReturn((String[]) null).when(jsonIgnoreProperties0).value();
        JsonIgnoreProperties.Value jsonIgnoreProperties_Value0 = JsonIgnoreProperties.Value.from(jsonIgnoreProperties0);
        Set<String> set0 = jsonIgnoreProperties_Value0.findIgnoredForSerialization();
        JsonIgnoreProperties.Value jsonIgnoreProperties_Value1 = jsonIgnoreProperties_Value0.withIgnored(set0);
        boolean boolean0 = jsonIgnoreProperties_Value0.equals(jsonIgnoreProperties_Value1);
        assertTrue(boolean0);
        assertFalse(jsonIgnoreProperties_Value1.getMerge());
    }
}
