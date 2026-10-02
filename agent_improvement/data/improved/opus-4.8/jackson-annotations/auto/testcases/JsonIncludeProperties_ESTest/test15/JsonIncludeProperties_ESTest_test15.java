package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.LinkedHashSet;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonIncludeProperties_ESTest_test15 extends JsonIncludeProperties_ESTest_scaffolding {

    /**
     * withOverrides(null) should be a no-op: overriding with an "undefined" Value
     * (here, a null override) returns the original Value instance unchanged.
     */
    @Test(timeout = 4000)
    public void withOverridesNullReturnsSameInstance() throws Throwable {
        LinkedHashSet<String> includedProperties = new LinkedHashSet<String>();
        Boolean ordered = Boolean.TRUE;
        JsonIncludeProperties.Value original =
                new JsonIncludeProperties.Value(includedProperties, ordered);

        JsonIncludeProperties.Value result = original.withOverrides((JsonIncludeProperties.Value) null);

        assertSame(original, result);
    }
}
