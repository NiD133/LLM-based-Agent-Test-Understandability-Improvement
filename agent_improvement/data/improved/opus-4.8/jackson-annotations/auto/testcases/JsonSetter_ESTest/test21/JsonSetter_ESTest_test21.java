package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonSetter_ESTest_test21 extends JsonSetter_ESTest_scaffolding {

    /**
     * When a JsonSetter.Value holds non-default null-handling settings, its
     * readResolve() (used during JDK deserialization) must return an instance
     * that preserves those settings rather than collapsing to the shared EMPTY
     * instance, which is reserved for all-DEFAULT values.
     */
    @Test(timeout = 4000)
    public void readResolvePreservesNonDefaultNullSettings() throws Throwable {
        JsonSetter.Value value = JsonSetter.Value.construct(Nulls.FAIL, Nulls.FAIL);

        JsonSetter.Value resolved = (JsonSetter.Value) value.readResolve();

        assertEquals(Nulls.FAIL, resolved.getValueNulls());
        assertEquals(Nulls.FAIL, resolved.getContentNulls());
    }
}
