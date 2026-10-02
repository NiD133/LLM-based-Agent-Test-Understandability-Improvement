package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JacksonInject_ESTest_test00 extends JacksonInject_ESTest_scaffolding {

    /**
     * Verifies that {@link JacksonInject.Value#construct(Object, Boolean, Boolean)}
     * stores the supplied {@code useInput} flag, so that {@code getUseInput()}
     * returns the same value that was passed in.
     */
    @Test(timeout = 4000)
    public void constructStoresUseInputFlag() throws Throwable {
        Boolean useInput = Boolean.TRUE;
        Boolean optional = Boolean.TRUE;

        JacksonInject.Value value =
                JacksonInject.Value.construct((Object) null, useInput, optional);

        assertTrue("getUseInput() should reflect the useInput argument",
                value.getUseInput());
    }
}
