package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JacksonInject_ESTest_test26 extends JacksonInject_ESTest_scaffolding {

    /**
     * A Value constructed with a non-null id (here the EMPTY instance itself is
     * reused as the id) is distinct from the EMPTY value, and reports having an id.
     */
    @Test(timeout = 4000)
    public void constructedValueWithIdDiffersFromEmptyAndHasId() throws Throwable {
        JacksonInject.Value emptyValue = JacksonInject.Value.EMPTY;
        Boolean useInputAndOptionalFlag = Boolean.FALSE;

        JacksonInject.Value valueWithId = JacksonInject.Value.construct(
                (Object) emptyValue, useInputAndOptionalFlag, useInputAndOptionalFlag);

        assertFalse(emptyValue.equals(valueWithId));
        assertTrue(valueWithId.hasId());
    }
}
