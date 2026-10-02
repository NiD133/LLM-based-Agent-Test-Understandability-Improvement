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
public class JsonInclude_ESTest_test28 extends JsonInclude_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test28() throws Throwable {
        JsonInclude.Include jsonInclude_Include0 = JsonInclude.Include.NON_ABSENT;
        JsonInclude.Value jsonInclude_Value0 = JsonInclude.Value.construct(jsonInclude_Include0, jsonInclude_Include0);
        JsonInclude.Include jsonInclude_Include1 = JsonInclude.Include.USE_DEFAULTS;
        JsonInclude.Value jsonInclude_Value1 = jsonInclude_Value0.withValueInclusion(jsonInclude_Include1);
        JsonInclude.Value jsonInclude_Value2 = (JsonInclude.Value) jsonInclude_Value1.readResolve();
        assertNotSame(jsonInclude_Value2, jsonInclude_Value0);
        assertFalse(jsonInclude_Value2.equals((Object) jsonInclude_Value0));
        assertEquals(JsonInclude.Include.NON_ABSENT, jsonInclude_Value2.getContentInclusion());
    }
}
