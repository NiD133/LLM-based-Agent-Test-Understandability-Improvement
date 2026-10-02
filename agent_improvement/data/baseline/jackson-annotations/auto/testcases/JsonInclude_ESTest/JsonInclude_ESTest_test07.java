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
public class JsonInclude_ESTest_test07 extends JsonInclude_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test07() throws Throwable {
        JsonInclude.Include jsonInclude_Include0 = JsonInclude.Include.NON_ABSENT;
        Class<Object> class0 = Object.class;
        JsonInclude jsonInclude0 = mock(JsonInclude.class, CALLS_REAL_METHODS);
        doReturn(jsonInclude_Include0).when(jsonInclude0).content();
        doReturn(class0).when(jsonInclude0).contentFilter();
        doReturn(jsonInclude_Include0).when(jsonInclude0).value();
        doReturn(class0).when(jsonInclude0).valueFilter();
        JsonInclude.Value jsonInclude_Value0 = JsonInclude.Value.from(jsonInclude0);
        JsonInclude.Value jsonInclude_Value1 = new JsonInclude.Value(jsonInclude_Include0, jsonInclude_Include0, class0, class0);
        boolean boolean0 = jsonInclude_Value0.equals(jsonInclude_Value1);
        assertTrue(boolean0);
        assertEquals(JsonInclude.Include.NON_ABSENT, jsonInclude_Value1.getContentInclusion());
        assertEquals(JsonInclude.Include.NON_ABSENT, jsonInclude_Value1.getValueInclusion());
    }
}
