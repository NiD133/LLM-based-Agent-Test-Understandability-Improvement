package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JacksonInject_ESTest_test22 extends JacksonInject_ESTest_scaffolding {

    /**
     * When the id passed to {@code construct} is an empty String, it is
     * normalized to {@code null}. The resulting Value therefore reports that
     * it has no injection id, even though the "optional" flag is set.
     */
    @Test(timeout = 4000)
    public void constructWithEmptyStringIdReportsNoId() throws Throwable {
        String emptyId = "";
        Boolean useInput = null;
        Boolean optional = Boolean.FALSE;

        JacksonInject.Value value = JacksonInject.Value.construct(emptyId, useInput, optional);

        assertFalse(value.hasId());
    }
}
