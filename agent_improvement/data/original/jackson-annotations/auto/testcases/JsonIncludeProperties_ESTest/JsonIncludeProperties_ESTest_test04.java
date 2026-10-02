package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.LinkedHashSet;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonIncludeProperties_ESTest_test04 extends JsonIncludeProperties_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test04() throws Throwable {
        LinkedHashSet<String> linkedHashSet0 = new LinkedHashSet<String>();
        Boolean boolean0 = new Boolean("j:w.BxrN!bO}");
        JsonIncludeProperties.Value jsonIncludeProperties_Value0 = new JsonIncludeProperties.Value(linkedHashSet0, boolean0);
        JsonIncludeProperties.Value jsonIncludeProperties_Value1 = new JsonIncludeProperties.Value(linkedHashSet0, boolean0);
        boolean boolean1 = jsonIncludeProperties_Value1.equals(jsonIncludeProperties_Value0);
        assertTrue(boolean1);
    }
}
