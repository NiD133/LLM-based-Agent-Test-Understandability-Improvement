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
public class JsonIncludeProperties_ESTest_test15 extends JsonIncludeProperties_ESTest_scaffolding {

    /**
     * Verifies that {@code withOverrides(null)} returns the same {@code Value} instance
     * unchanged, because a {@code null} override is treated as "not defined" and the
     * original value takes precedence.
     */
    @Test(timeout = 4000)
    public void test_withNullOverrides_returnsSameInstance() throws Throwable {
        LinkedHashSet<String> emptyIncludedProperties = new LinkedHashSet<String>();
        Boolean ordered = Boolean.valueOf(true);
        JsonIncludeProperties.Value original = new JsonIncludeProperties.Value(emptyIncludedProperties, ordered);

        JsonIncludeProperties.Value result = original.withOverrides((JsonIncludeProperties.Value) null);

        assertSame(original, result);
    }
}
