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
public class JsonInclude_ESTest_test10 extends JsonInclude_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test10() throws Throwable {
        JsonInclude.Value jsonInclude_Value0 = JsonInclude.Value.ALL_NON_ABSENT;
        JsonInclude.Include jsonInclude_Include0 = JsonInclude.Include.NON_ABSENT;
        JsonInclude.Value jsonInclude_Value1 = jsonInclude_Value0.withContentInclusion(jsonInclude_Include0);
        assertSame(jsonInclude_Value1, jsonInclude_Value0);
    }
}
