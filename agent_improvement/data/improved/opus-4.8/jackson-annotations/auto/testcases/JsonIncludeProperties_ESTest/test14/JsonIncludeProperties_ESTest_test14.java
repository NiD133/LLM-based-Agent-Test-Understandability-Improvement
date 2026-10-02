package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.LinkedHashSet;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonIncludeProperties_ESTest_test14 extends JsonIncludeProperties_ESTest_scaffolding {

    /**
     * Verifies that overriding a Value whose included set is defined (here, empty)
     * with an "undefined" Value (included == null) leaves the original unchanged:
     * withOverrides() returns the original instance as-is.
     */
    @Test(timeout = 4000)
    public void overridingWithUndefinedValueReturnsOriginalUnchanged() throws Throwable {
        // Value with an explicitly defined (empty) set of included properties.
        LinkedHashSet<String> includedProperties = new LinkedHashSet<String>();
        JsonIncludeProperties.Value definedValue =
                new JsonIncludeProperties.Value(includedProperties, (Boolean) null);

        // Value.from(null) yields the ALL instance, which is "undefined" (getIncluded() == null).
        JsonIncludeProperties.Value undefinedValue =
                JsonIncludeProperties.Value.from((JsonIncludeProperties) null);

        // Overriding with an "undefined" Value should return the original Value as-is.
        JsonIncludeProperties.Value result = definedValue.withOverrides(undefinedValue);

        // The result is the unchanged original, not the undefined override.
        assertFalse(result.equals((Object) undefinedValue));
        assertSame(definedValue, result);
    }
}
