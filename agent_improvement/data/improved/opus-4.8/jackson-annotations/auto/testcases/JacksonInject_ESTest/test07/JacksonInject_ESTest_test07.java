package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JacksonInject_ESTest_test07 extends JacksonInject_ESTest_scaffolding {

    /**
     * When a Value has useInput explicitly set to TRUE, willUseInput() should
     * return that configured value (true) and ignore the supplied default setting.
     */
    @Test(timeout = 4000)
    public void willUseInput_returnsConfiguredUseInput_ignoringDefault() throws Throwable {
        JacksonInject.Value valueWithUseInputTrue =
                JacksonInject.Value.EMPTY.withUseInput(Boolean.TRUE);

        boolean useInput = valueWithUseInputTrue.willUseInput(false);

        assertTrue(useInput);
    }
}
