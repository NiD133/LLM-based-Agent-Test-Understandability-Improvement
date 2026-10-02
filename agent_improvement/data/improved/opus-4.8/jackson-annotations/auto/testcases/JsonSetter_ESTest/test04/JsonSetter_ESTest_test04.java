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
     * A JsonSetter.Value is never equal to a plain Object: equals() requires the
     * other instance to be of the same class, so comparing against an unrelated
     * Object must return false.
     */
    @Test(timeout = 4000)
    public void valueIsNotEqualToPlainObject() throws Throwable {
        JsonSetter.Value value = JsonSetter.Value.forContentNulls(Nulls.DEFAULT);

        boolean equalToPlainObject = value.equals(new Object());

        assertFalse(equalToPlainObject);
    }
}
