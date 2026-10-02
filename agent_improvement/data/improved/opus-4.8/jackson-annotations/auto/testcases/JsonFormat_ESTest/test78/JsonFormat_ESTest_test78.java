package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonFormat_ESTest_test78 extends JsonFormat_ESTest_scaffolding {

    /**
     * The empty JsonFormat.Value should expose an empty (not null) pattern,
     * matching the default supplied by its no-argument constructor.
     */
    @Test(timeout = 4000)
    public void emptyValueHasEmptyPattern() throws Throwable {
        JsonFormat.Value emptyValue = JsonFormat.Value.empty();

        String pattern = emptyValue.getPattern();

        assertEquals("", pattern);
    }
}
