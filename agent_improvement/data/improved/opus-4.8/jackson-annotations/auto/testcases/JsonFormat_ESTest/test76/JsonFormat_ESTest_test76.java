package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonFormat_ESTest_test76 extends JsonFormat_ESTest_scaffolding {

    /**
     * A Value built from only a Shape carries that shape but no other settings.
     * Verifies that JsonFormat.Value.forShape(ARRAY) reports a shape while leaving
     * the radix at its default and the locale unset.
     */
    @Test(timeout = 4000)
    public void forShape_setsShapeButLeavesRadixAndLocaleAtDefault() throws Throwable {
        JsonFormat.Value value = JsonFormat.Value.forShape(JsonFormat.Shape.ARRAY);

        assertNull("forShape should not set a locale", value.getLocale());
        assertFalse("a default radix is expected", value.hasNonDefaultRadix());
        assertTrue("the explicit ARRAY shape should be reported", value.hasShape());
    }
}
