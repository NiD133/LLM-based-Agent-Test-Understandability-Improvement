package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonInclude_ESTest_test00 extends JsonInclude_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test00() throws Throwable {
        JsonInclude.Value jsonInclude_Value0 = JsonInclude.Value.ALL_NON_EMPTY;
        Class<Integer> class0 = Integer.class;
        JsonInclude.Value jsonInclude_Value1 = jsonInclude_Value0.withContentFilter(class0);
        JsonInclude.Include jsonInclude_Include0 = JsonInclude.Include.CUSTOM;
        JsonInclude.Value jsonInclude_Value2 = jsonInclude_Value0.withContentInclusion(jsonInclude_Include0);
        boolean boolean0 = jsonInclude_Value2.equals(jsonInclude_Value1);
        assertEquals(JsonInclude.Include.NON_EMPTY, jsonInclude_Value1.getValueInclusion());
        assertFalse(jsonInclude_Value0.equals((Object) jsonInclude_Value2));
        assertFalse(boolean0);
        assertNotSame(jsonInclude_Value2, jsonInclude_Value0);
        assertFalse(jsonInclude_Value2.equals((Object) jsonInclude_Value0));
        assertEquals(JsonInclude.Include.CUSTOM, jsonInclude_Value1.getContentInclusion());
        assertFalse(jsonInclude_Value1.equals((Object) jsonInclude_Value2));
    }
}
