package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JacksonInject_ESTest_test02 extends JacksonInject_ESTest_scaffolding {

    /**
     * Verifies that {@code withUseInput} produces a distinct Value: applying a
     * non-null useInput flag to the EMPTY value yields a different instance, so
     * the two are not equal in either direction.
     */
    @Test(timeout = 4000)
    public void withUseInput_changesValue_soNotEqualToEmpty() throws Throwable {
        JacksonInject.Value emptyValue = JacksonInject.Value.EMPTY;

        JacksonInject.Value valueWithUseInput = emptyValue.withUseInput(Boolean.TRUE);

        assertFalse("EMPTY should not equal the value carrying useInput=true",
                emptyValue.equals(valueWithUseInput));
        assertFalse("equals should be symmetric: the modified value should not equal EMPTY",
                valueWithUseInput.equals((Object) emptyValue));
    }
}
