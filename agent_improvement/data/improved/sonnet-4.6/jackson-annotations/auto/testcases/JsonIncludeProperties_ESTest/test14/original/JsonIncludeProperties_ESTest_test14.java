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
public class JsonIncludeProperties_ESTest_test14 extends JsonIncludeProperties_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test14() throws Throwable {
        LinkedHashSet<String> linkedHashSet0 = new LinkedHashSet<String>();
        JsonIncludeProperties.Value jsonIncludeProperties_Value0 = new JsonIncludeProperties.Value(linkedHashSet0, (Boolean) null);
        JsonIncludeProperties.Value jsonIncludeProperties_Value1 = JsonIncludeProperties.Value.from((JsonIncludeProperties) null);
        JsonIncludeProperties.Value jsonIncludeProperties_Value2 = jsonIncludeProperties_Value0.withOverrides(jsonIncludeProperties_Value1);
        assertFalse(jsonIncludeProperties_Value2.equals((Object) jsonIncludeProperties_Value1));
        assertSame(jsonIncludeProperties_Value2, jsonIncludeProperties_Value0);
    }
}
