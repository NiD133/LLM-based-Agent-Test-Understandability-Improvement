package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonFormat_ESTest_test04 extends JsonFormat_ESTest_scaffolding {

    /**
     * A Value built only from a pattern should differ from a default Value, and
     * should not report having a shape, a non-default radix, or a time zone.
     */
    @Test(timeout = 4000)
    public void patternOnlyValue_differsFromDefaultAndHasNoOtherSettings() throws Throwable {
        JsonFormat.Value defaultValue = new JsonFormat.Value();
        JsonFormat.Value patternOnlyValue = JsonFormat.Value.forPattern("*h,2D`=nR6aV]Mg'.");

        // A pattern-only Value is not equal to a fully-default Value.
        assertFalse(patternOnlyValue.equals(defaultValue));

        // forPattern() leaves every other setting at its default.
        assertFalse(patternOnlyValue.hasShape());
        assertFalse(patternOnlyValue.hasNonDefaultRadix());
        assertFalse(patternOnlyValue.hasTimeZone());
    }
}
