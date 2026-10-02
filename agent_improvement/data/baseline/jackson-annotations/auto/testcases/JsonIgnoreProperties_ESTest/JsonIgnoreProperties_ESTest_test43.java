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
        LinkedHashSet<String> linkedHashSet0 = new LinkedHashSet<String>();
        JsonIgnoreProperties.Value jsonIgnoreProperties_Value0 = JsonIgnoreProperties.Value.forIgnoredProperties((Set<String>) linkedHashSet0);
        assertFalse(jsonIgnoreProperties_Value0.getAllowSetters());
        assertFalse(jsonIgnoreProperties_Value0.getIgnoreUnknown());
        assertFalse(jsonIgnoreProperties_Value0.getAllowGetters());
        assertTrue(jsonIgnoreProperties_Value0.getMerge());
    }
}
