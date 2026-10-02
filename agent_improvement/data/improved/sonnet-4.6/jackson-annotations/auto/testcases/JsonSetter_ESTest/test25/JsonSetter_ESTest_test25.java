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
public class JsonSetter_ESTest_test25 extends JsonSetter_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test25() throws Throwable {
        // Build a Value with FAIL for both value-nulls and content-nulls
        Nulls failNulls = Nulls.FAIL;
        JsonSetter.Value valueWithFailNulls = JsonSetter.Value.forValueNulls(failNulls, failNulls);

        // merge(null, overrides) should return overrides unchanged when base is null
        JsonSetter.Value mergedValue = JsonSetter.Value.merge(null, valueWithFailNulls);

        assertNotNull(mergedValue);
        assertEquals(Nulls.FAIL, mergedValue.getContentNulls());
        assertEquals(Nulls.FAIL, mergedValue.getValueNulls());
    }
}
