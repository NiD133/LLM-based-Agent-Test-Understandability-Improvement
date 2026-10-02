package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JacksonInject_ESTest_test24 extends JacksonInject_ESTest_scaffolding {

    /**
     * Verifies that {@code Value.construct(id, useInput, optional)} keeps the
     * provided {@code useInput} flag (retrievable via {@code getUseInput()})
     * and that a non-null id makes {@code hasId()} report true.
     */
    @Test(timeout = 4000)
    public void constructStoresUseInputAndExposesId() throws Throwable {
        Object injectionId = new Object();
        Boolean useInput = Boolean.TRUE;
        Boolean optional = Boolean.TRUE;

        JacksonInject.Value value =
                JacksonInject.Value.construct(injectionId, useInput, optional);

        assertNotNull("useInput flag should be retained", value.getUseInput());
        assertTrue("a non-null id should be reported by hasId()", value.hasId());
    }
}
