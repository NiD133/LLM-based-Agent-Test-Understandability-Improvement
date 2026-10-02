package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JacksonInject_ESTest_test25 extends JacksonInject_ESTest_scaffolding {

    /**
     * A Value constructed with a non-null id (and no useInput/optional flags)
     * should report that id back via getId() and answer true to hasId().
     */
    @Test(timeout = 4000)
    public void valueWithNonNullId_reportsIdAndHasId() throws Throwable {
        Object injectedId = new Object();

        JacksonInject.Value value = new JacksonInject.Value(injectedId, (Boolean) null, (Boolean) null);

        assertNotNull("getId() should return the id supplied to the constructor", value.getId());
        assertTrue("hasId() should be true when an id is present", value.hasId());
    }
}
