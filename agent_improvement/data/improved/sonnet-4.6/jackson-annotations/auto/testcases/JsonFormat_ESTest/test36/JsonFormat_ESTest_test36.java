package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonFormat_ESTest_test36 extends JsonFormat_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test36_mergeWithNullOverridePreservesBaseRadixAndDefaultShape() throws Throwable {
        JsonFormat.Value baseValue = JsonFormat.Value.forRadix(-1639);
        JsonFormat.Value mergedValue = JsonFormat.Value.merge(baseValue, (JsonFormat.Value) null);

        assertFalse(mergedValue.hasShape());
        assertEquals(-1639, mergedValue.getRadix());
        assertNotNull(mergedValue);
    }
}
