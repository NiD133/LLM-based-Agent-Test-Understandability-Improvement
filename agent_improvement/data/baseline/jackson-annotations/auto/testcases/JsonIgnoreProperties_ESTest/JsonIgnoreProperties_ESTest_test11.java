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
public class JsonIgnoreProperties_ESTest_test11 extends JsonIgnoreProperties_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test11() throws Throwable {
        JsonIgnoreProperties.Value[] jsonIgnoreProperties_ValueArray0 = new JsonIgnoreProperties.Value[6];
        JsonIgnoreProperties.Value jsonIgnoreProperties_Value0 = JsonIgnoreProperties.Value.empty();
        Set<String> set0 = jsonIgnoreProperties_Value0.getIgnored();
        JsonIgnoreProperties.Value jsonIgnoreProperties_Value1 = new JsonIgnoreProperties.Value(set0, true, true, true, true);
        jsonIgnoreProperties_ValueArray0[1] = jsonIgnoreProperties_Value1;
        Set<String> set1 = jsonIgnoreProperties_ValueArray0[1].findIgnoredForDeserialization();
        assertTrue(set1.isEmpty());
    }
}
