package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonSetter_ESTest_test04 extends JsonSetter_ESTest_scaffolding {

    /**
     * A JsonSetter.Value is never considered equal to a plain Object,
     * since equals() returns false for any argument whose class differs.
     */
    @Test(timeout = 4000)
    public void valueIsNotEqualToPlainObject() throws Throwable {
        JsonSetter.Value contentNullsValue = JsonSetter.Value.forContentNulls(Nulls.DEFAULT);

        boolean equalsPlainObject = contentNullsValue.equals(new Object());

        assertFalse(equalsPlainObject);
    }
}
