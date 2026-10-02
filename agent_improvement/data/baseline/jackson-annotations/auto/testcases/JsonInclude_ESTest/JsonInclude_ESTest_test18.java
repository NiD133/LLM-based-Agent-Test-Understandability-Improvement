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
public class JsonInclude_ESTest_test18 extends JsonInclude_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test18() throws Throwable {
        JsonInclude.Value jsonInclude_Value0 = JsonInclude.Value.construct((JsonInclude.Include) null, (JsonInclude.Include) null);
        assertEquals(JsonInclude.Include.USE_DEFAULTS, jsonInclude_Value0.getContentInclusion());
    }
}
