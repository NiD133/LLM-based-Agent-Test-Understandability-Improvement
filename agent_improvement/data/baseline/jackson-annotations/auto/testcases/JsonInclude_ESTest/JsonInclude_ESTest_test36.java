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
public class JsonInclude_ESTest_test36 extends JsonInclude_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test36() throws Throwable {
        JsonInclude.Value jsonInclude_Value0 = JsonInclude.Value.empty();
        JsonInclude.Value jsonInclude_Value1 = jsonInclude_Value0.withOverrides((JsonInclude.Value) null);
        assertSame(jsonInclude_Value1, jsonInclude_Value0);
    }
}
