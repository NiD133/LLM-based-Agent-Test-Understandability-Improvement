package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JacksonInject_ESTest_test21 extends JacksonInject_ESTest_scaffolding {

    /**
     * Value.forId(id) only sets the injection id and leaves "useInput" unset,
     * so the resulting Value should report a null useInput flag.
     */
    @Test(timeout = 4000)
    public void forId_leavesUseInputUnset() throws Throwable {
        JacksonInject.Value valueWithNullId = JacksonInject.Value.forId((Object) null);

        assertNull(valueWithNullId.getUseInput());
    }
}
