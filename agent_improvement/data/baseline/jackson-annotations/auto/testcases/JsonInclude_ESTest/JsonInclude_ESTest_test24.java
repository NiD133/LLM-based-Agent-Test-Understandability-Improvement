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
public class JsonInclude_ESTest_test24 extends JsonInclude_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test24() throws Throwable {
        JsonInclude.Value jsonInclude_Value0 = JsonInclude.Value.ALL_NON_EMPTY;
        Class<Object> class0 = Object.class;
        JsonInclude.Value jsonInclude_Value1 = jsonInclude_Value0.withValueFilter(class0);
        JsonInclude.Value jsonInclude_Value2 = jsonInclude_Value1.withOverrides(jsonInclude_Value0);
        assertTrue(jsonInclude_Value2.equals((Object) jsonInclude_Value0));
        assertEquals(JsonInclude.Include.NON_EMPTY, jsonInclude_Value1.getContentInclusion());
        assertEquals(JsonInclude.Include.CUSTOM, jsonInclude_Value1.getValueInclusion());
    }
}
