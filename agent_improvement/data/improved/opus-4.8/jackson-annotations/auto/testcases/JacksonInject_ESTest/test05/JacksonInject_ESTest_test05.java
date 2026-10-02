package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JacksonInject_ESTest_test05 extends JacksonInject_ESTest_scaffolding {

    /**
     * A Value created with a non-null id should report that it has an id,
     * and should be considered equal to itself (reflexive equality).
     */
    @Test(timeout = 4000)
    public void valueWithId_hasId_andEqualsItself() throws Throwable {
        Object injectionId = new Object();
        JacksonInject.Value valueWithId = JacksonInject.Value.forId(injectionId);

        assertTrue("Value built from a non-null id should report hasId()",
                valueWithId.hasId());
        assertTrue("A Value should be equal to itself",
                valueWithId.equals(valueWithId));
    }
}
