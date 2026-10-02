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
public class JsonInclude_ESTest_test15 extends JsonInclude_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test15() throws Throwable {
        JsonInclude.Include jsonInclude_Include0 = JsonInclude.Include.NON_ABSENT;
        Class<Object> class0 = Object.class;
        JsonInclude.Include jsonInclude_Include1 = JsonInclude.Include.USE_DEFAULTS;
        Class<Integer> class1 = Integer.class;
        JsonInclude jsonInclude0 = mock(JsonInclude.class, CALLS_REAL_METHODS);
        doReturn(jsonInclude_Include0).when(jsonInclude0).content();
        doReturn(class0).when(jsonInclude0).contentFilter();
        doReturn(jsonInclude_Include1).when(jsonInclude0).value();
        doReturn(class1).when(jsonInclude0).valueFilter();
        JsonInclude.Value jsonInclude_Value0 = JsonInclude.Value.from(jsonInclude0);
        assertEquals(JsonInclude.Include.NON_ABSENT, jsonInclude_Value0.getContentInclusion());
        assertEquals(JsonInclude.Include.USE_DEFAULTS, jsonInclude_Value0.getValueInclusion());
    }
}
