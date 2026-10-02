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
public class JsonInclude_ESTest_test14 extends JsonInclude_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test14() throws Throwable {
        JsonInclude.Include jsonInclude_Include0 = JsonInclude.Include.USE_DEFAULTS;
        JsonInclude jsonInclude0 = mock(JsonInclude.class, CALLS_REAL_METHODS);
        doReturn(jsonInclude_Include0).when(jsonInclude0).content();
        doReturn(jsonInclude_Include0).when(jsonInclude0).value();
        JsonInclude.Value jsonInclude_Value0 = JsonInclude.Value.from(jsonInclude0);
        assertEquals(JsonInclude.Include.USE_DEFAULTS, jsonInclude_Value0.getContentInclusion());
    }
}
