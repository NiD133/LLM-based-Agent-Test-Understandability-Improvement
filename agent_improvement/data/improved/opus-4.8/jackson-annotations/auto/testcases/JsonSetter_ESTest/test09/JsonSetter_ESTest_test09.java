package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonSetter_ESTest_test09 extends JsonSetter_ESTest_scaffolding {

    /**
     * Verifies that {@code withContentNulls(null)} produces a new Value whose
     * content-nulls setting is reset to {@link Nulls#DEFAULT}, while the original
     * Value is left unchanged and the value-nulls setting is carried over.
     */
    @Test(timeout = 4000)
    public void withContentNulls_givenNull_resetsContentNullsToDefault() throws Throwable {
        JsonSetter.Value original = JsonSetter.Value.construct(Nulls.FAIL, Nulls.FAIL);

        JsonSetter.Value updated = original.withContentNulls((Nulls) null);

        // Original instance is unaffected by the mutant factory method.
        assertEquals(Nulls.FAIL, original.getContentNulls());

        // Passing null resets contentNulls to DEFAULT on the new instance...
        assertEquals(Nulls.DEFAULT, updated.getContentNulls());
        // ...while the valueNulls setting is preserved from the original.
        assertEquals(Nulls.FAIL, updated.getValueNulls());
    }
}
