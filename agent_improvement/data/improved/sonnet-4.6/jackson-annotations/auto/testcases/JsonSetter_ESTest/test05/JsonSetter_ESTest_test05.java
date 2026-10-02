package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonSetter_ESTest_test05 extends JsonSetter_ESTest_scaffolding {

    /**
     * EMPTY has Nulls.DEFAULT for contentNulls, so nonDefaultContentNulls()
     * returns null to signal "no non-default override is present".
     */
    @Test(timeout = 4000)
    public void test05_emptyValueReturnsNullForNonDefaultContentNulls() throws Throwable {
        JsonSetter.Value emptyValue = JsonSetter.Value.EMPTY;
        Nulls contentNulls = emptyValue.nonDefaultContentNulls();
        assertNull(contentNulls);
    }
}
