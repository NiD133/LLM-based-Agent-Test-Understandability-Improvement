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
public class JsonIgnoreProperties_ESTest_test08 extends JsonIgnoreProperties_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test08() throws Throwable {
        LinkedHashSet<String> linkedHashSet0 = new LinkedHashSet<String>();
        JsonIgnoreProperties.Value jsonIgnoreProperties_Value0 = JsonIgnoreProperties.Value.construct(linkedHashSet0, false, false, false, true);
        JsonIgnoreProperties.Value jsonIgnoreProperties_Value1 = jsonIgnoreProperties_Value0.withoutMerge();
        LinkedHashSet<Object> linkedHashSet1 = new LinkedHashSet<Object>();
        linkedHashSet1.contains(jsonIgnoreProperties_Value1);
        assertFalse(jsonIgnoreProperties_Value0.getIgnoreUnknown());
        assertFalse(jsonIgnoreProperties_Value1.getIgnoreUnknown());
        assertFalse(jsonIgnoreProperties_Value1.getMerge());
        assertFalse(jsonIgnoreProperties_Value1.getAllowSetters());
        assertFalse(jsonIgnoreProperties_Value1.getAllowGetters());
        assertFalse(jsonIgnoreProperties_Value0.equals((Object) jsonIgnoreProperties_Value1));
    }
}
