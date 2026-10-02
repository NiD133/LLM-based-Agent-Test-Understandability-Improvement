package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonFormat_ESTest_test15 extends JsonFormat_ESTest_scaffolding {

    /**
     * The empty (default) JsonFormat.Value carries no timezone, so
     * hasTimeZone() should report false.
     */
    @Test(timeout = 4000)
    public void emptyValue_hasNoTimeZone() throws Throwable {
        JsonFormat.Value emptyFormat = JsonFormat.Value.empty();

        boolean hasTimeZone = emptyFormat.hasTimeZone();

        assertFalse(hasTimeZone);
    }
}
