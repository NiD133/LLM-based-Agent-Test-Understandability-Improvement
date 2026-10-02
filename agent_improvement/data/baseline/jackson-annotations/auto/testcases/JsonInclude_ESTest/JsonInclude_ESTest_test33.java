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
public class JsonInclude_ESTest_test33 extends JsonInclude_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test33() throws Throwable {
        JsonInclude jsonInclude0 = mock(JsonInclude.class, CALLS_REAL_METHODS);
        doReturn((JsonInclude.Include) null).when(jsonInclude0).content();
        doReturn((Class) null).when(jsonInclude0).contentFilter();
        doReturn((JsonInclude.Include) null).when(jsonInclude0).value();
        doReturn((Class) null).when(jsonInclude0).valueFilter();
        JsonInclude.Value jsonInclude_Value0 = new JsonInclude.Value(jsonInclude0);
        JsonInclude.Include jsonInclude_Include0 = jsonInclude_Value0.getValueInclusion();
        assertEquals(JsonInclude.Include.USE_DEFAULTS, jsonInclude_Include0);
    }
}
