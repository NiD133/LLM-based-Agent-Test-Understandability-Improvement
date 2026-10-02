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
public class JsonInclude_ESTest_test30 extends JsonInclude_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test30() throws Throwable {
        JsonInclude.Value jsonInclude_Value0 = JsonInclude.Value.from((JsonInclude) null);
        JsonInclude.Value[] jsonInclude_ValueArray0 = new JsonInclude.Value[9];
        jsonInclude_ValueArray0[0] = jsonInclude_Value0;
        jsonInclude_ValueArray0[1] = jsonInclude_Value0;
        JsonInclude.Value jsonInclude_Value1 = JsonInclude.Value.mergeAll(jsonInclude_ValueArray0);
        assertEquals(JsonInclude.Include.USE_DEFAULTS, jsonInclude_Value1.getValueInclusion());
        assertNotNull(jsonInclude_Value1);
    }
}
