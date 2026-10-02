package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.LinkedHashSet;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonIncludeProperties_ESTest_test13 extends JsonIncludeProperties_ESTest_scaffolding {

    /**
     * Verifies that overriding an "undefined" Value (one whose included set is null,
     * such as the ALL instance returned by from(null)) returns the override Value as-is.
     */
    @Test(timeout = 4000)
    public void withOverridesOnUndefinedValueReturnsOverride() throws Throwable {
        // An override Value with an explicit (here empty) set of included properties.
        LinkedHashSet<String> includedProperties = new LinkedHashSet<String>();
        JsonIncludeProperties.Value overrideValue =
                new JsonIncludeProperties.Value(includedProperties, (Boolean) null);

        // from(null) yields the "undefined" ALL Value, whose getIncluded() is null.
        JsonIncludeProperties.Value undefinedValue =
                JsonIncludeProperties.Value.from((JsonIncludeProperties) null);

        JsonIncludeProperties.Value result = undefinedValue.withOverrides(overrideValue);

        // Overriding an undefined Value returns the override Value unchanged.
        assertSame(overrideValue, result);
    }
}
