package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.lang.reflect.Member;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonAutoDetect_ESTest_test12 extends JsonAutoDetect_ESTest_scaffolding {

    /**
     * Verifies that after deserialization (readResolve), the NO_OVERRIDES constant
     * retains DEFAULT setter visibility, confirming no visibility overrides are applied.
     */
    @Test(timeout = 4000)
    public void test12() throws Throwable {
        JsonAutoDetect.Value noOverrides = JsonAutoDetect.Value.NO_OVERRIDES;
        JsonAutoDetect.Value resolvedValue = (JsonAutoDetect.Value) noOverrides.readResolve();
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, resolvedValue.getSetterVisibility());
    }
}
