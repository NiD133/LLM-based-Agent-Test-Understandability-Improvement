package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonSetter_ESTest_test22 extends JsonSetter_ESTest_scaffolding {

    /**
     * Verifies that {@link JsonSetter.Value#toString()} renders both the value-nulls
     * and content-nulls settings using the configured {@link Nulls} constants.
     */
    @Test(timeout = 4000)
    public void toString_reportsValueAndContentNulls() throws Throwable {
        JsonSetter.Value value = JsonSetter.Value.construct(Nulls.FAIL, Nulls.FAIL);

        String description = value.toString();

        assertEquals("JsonSetter.Value(valueNulls=FAIL,contentNulls=FAIL)", description);
    }
}
