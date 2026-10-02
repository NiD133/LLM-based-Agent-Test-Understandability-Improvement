package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.LinkedHashSet;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonIncludeProperties_ESTest_test13 extends JsonIncludeProperties_ESTest_scaffolding {

    /**
     * When withOverrides() is called on the "all-included" (undefined) Value singleton,
     * it should return the override instance unchanged, because an undefined base
     * defers entirely to the override.
     */
    @Test(timeout = 4000)
    public void test13() throws Throwable {
        // Build an override Value whose included-set is empty (no properties allowed)
        // and whose ordering flag is unset (null).
        LinkedHashSet<String> emptyIncludedProperties = new LinkedHashSet<String>();
        JsonIncludeProperties.Value overrideValue =
                new JsonIncludeProperties.Value(emptyIncludedProperties, (Boolean) null);

        // Value.from(null) returns the ALL singleton, which has _included == null
        // (meaning "include everything" / undefined).
        JsonIncludeProperties.Value allIncludedValue =
                JsonIncludeProperties.Value.from((JsonIncludeProperties) null);

        // Calling withOverrides on an undefined Value should return the override as-is
        // (documented contract: "overriding an 'undefined' Value returns override as-is").
        JsonIncludeProperties.Value result = allIncludedValue.withOverrides(overrideValue);

        assertSame(result, overrideValue);
    }
}
