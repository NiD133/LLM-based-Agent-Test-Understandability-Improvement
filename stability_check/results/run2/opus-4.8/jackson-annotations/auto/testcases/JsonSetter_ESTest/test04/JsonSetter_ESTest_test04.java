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
     * A JsonSetter.Value must not be considered equal to a plain Object,
     * since equals() only returns true for another JsonSetter.Value with
     * matching null-handling settings.
     */
    @Test(timeout = 4000)
    public void valueIsNotEqualToPlainObject() throws Throwable {
        JsonSetter.Value setterValue = JsonSetter.Value.forContentNulls(Nulls.DEFAULT);

        boolean equalToPlainObject = setterValue.equals(new Object());

        assertFalse(equalToPlainObject);
    }
}
