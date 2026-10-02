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
public class JsonIgnoreProperties_ESTest_test37 extends JsonIgnoreProperties_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test37() throws Throwable {
        JsonIgnoreProperties jsonIgnoreProperties0 = mock(JsonIgnoreProperties.class, CALLS_REAL_METHODS);
        doReturn(false).when(jsonIgnoreProperties0).allowGetters();
        doReturn(false).when(jsonIgnoreProperties0).allowSetters();
        doReturn(false).when(jsonIgnoreProperties0).ignoreUnknown();
        doReturn((String[]) null).when(jsonIgnoreProperties0).value();
        JsonIgnoreProperties.Value jsonIgnoreProperties_Value0 = JsonIgnoreProperties.Value.from(jsonIgnoreProperties0);
        Object object0 = new Object();
        boolean boolean0 = jsonIgnoreProperties_Value0.equals(object0);
        assertFalse(boolean0);
        assertFalse(jsonIgnoreProperties_Value0.getMerge());
        assertFalse(jsonIgnoreProperties_Value0.getIgnoreUnknown());
        assertFalse(jsonIgnoreProperties_Value0.getAllowGetters());
        assertFalse(jsonIgnoreProperties_Value0.getAllowSetters());
    }
}
