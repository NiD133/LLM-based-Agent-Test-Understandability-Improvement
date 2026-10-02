package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonSetter_ESTest_test23 extends JsonSetter_ESTest_scaffolding {

    /**
     * The empty Value uses Nulls.DEFAULT for both value and content null-handling.
     * Deserializing it via readResolve() should resolve back to the shared EMPTY
     * instance, which still reports Nulls.DEFAULT for content nulls.
     */
    @Test(timeout = 4000)
    public void readResolveOfEmptyValueKeepsDefaultContentNulls() throws Throwable {
        JsonSetter.Value emptyValue = JsonSetter.Value.empty();

        JsonSetter.Value resolvedValue = (JsonSetter.Value) emptyValue.readResolve();

        assertEquals(Nulls.DEFAULT, resolvedValue.getContentNulls());
    }
}
