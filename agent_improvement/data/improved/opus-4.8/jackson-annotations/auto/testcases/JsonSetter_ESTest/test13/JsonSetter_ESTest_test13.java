package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonSetter_ESTest_test13 extends JsonSetter_ESTest_scaffolding {

    /**
     * Verifies that {@link JsonSetter.Value#withValueNulls(Nulls)} is a mutant
     * factory: passing {@code null} produces a NEW Value whose valueNulls is
     * reset to {@link Nulls#DEFAULT} while contentNulls is preserved, and the
     * original Value instance is left unchanged.
     */
    @Test(timeout = 4000)
    public void withValueNulls_givenNull_resetsValueNullsToDefaultAndKeepsContentNulls() throws Throwable {
        // Start from a Value where both valueNulls and contentNulls are FAIL.
        JsonSetter.Value originalValue = JsonSetter.Value.construct(Nulls.FAIL, Nulls.FAIL);

        // Override valueNulls with null -> reset to DEFAULT, contentNulls untouched.
        JsonSetter.Value updatedValue = originalValue.withValueNulls((Nulls) null);

        // The returned Value reflects the override.
        assertEquals(Nulls.DEFAULT, updatedValue.getValueNulls());
        assertEquals(Nulls.FAIL, updatedValue.getContentNulls());

        // The original Value remains unchanged (immutability).
        assertEquals(Nulls.FAIL, originalValue.getValueNulls());
    }
}
