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
public class JsonInclude_ESTest_test21 extends JsonInclude_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test21() throws Throwable {
        JsonInclude.Value jsonInclude_Value0 = JsonInclude.Value.from((JsonInclude) null);
        JsonInclude.Value jsonInclude_Value1 = JsonInclude.Value.ALL_NON_ABSENT;
        JsonInclude.Value jsonInclude_Value2 = jsonInclude_Value0.withOverrides(jsonInclude_Value1);
        assertTrue(jsonInclude_Value2.equals((Object) jsonInclude_Value1));
        assertEquals(JsonInclude.Include.USE_DEFAULTS, jsonInclude_Value0.getValueInclusion());
    }
}
