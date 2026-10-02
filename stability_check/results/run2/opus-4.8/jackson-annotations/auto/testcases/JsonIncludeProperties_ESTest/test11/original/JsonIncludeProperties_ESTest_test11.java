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
public class JsonIncludeProperties_ESTest_test11 extends JsonIncludeProperties_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test11() throws Throwable {
        LinkedHashSet<String> linkedHashSet0 = new LinkedHashSet<String>();
        linkedHashSet0.add("$h]54GG#Ke5AZNb`7r");
        JsonIncludeProperties.Value jsonIncludeProperties_Value0 = new JsonIncludeProperties.Value(linkedHashSet0, (Boolean) null);
        OptBoolean optBoolean0 = OptBoolean.DEFAULT;
        String[] stringArray0 = new String[1];
        JsonIncludeProperties jsonIncludeProperties0 = mock(JsonIncludeProperties.class, CALLS_REAL_METHODS);
        doReturn(optBoolean0).when(jsonIncludeProperties0).order();
        doReturn(stringArray0).when(jsonIncludeProperties0).value();
        JsonIncludeProperties.Value jsonIncludeProperties_Value1 = JsonIncludeProperties.Value.from(jsonIncludeProperties0);
        JsonIncludeProperties.Value jsonIncludeProperties_Value2 = jsonIncludeProperties_Value1.withOverrides(jsonIncludeProperties_Value0);
        assertFalse(jsonIncludeProperties_Value2.equals((Object) jsonIncludeProperties_Value0));
        assertNotSame(jsonIncludeProperties_Value2, jsonIncludeProperties_Value0);
        assertFalse(jsonIncludeProperties_Value2.equals((Object) jsonIncludeProperties_Value1));
        assertNotSame(jsonIncludeProperties_Value2, jsonIncludeProperties_Value1);
    }
}
