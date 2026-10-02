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

    @Test(timeout = 4000)
    public void test12() throws Throwable {
        // Arrange: start from the canonical "no overrides" visibility value.
        JsonAutoDetect.Value noOverrides = JsonAutoDetect.Value.NO_OVERRIDES;

        // Act: resolve the serialized form back to the canonical instance.
        JsonAutoDetect.Value resolvedValue = (JsonAutoDetect.Value) noOverrides.readResolve();

        // Assert: "no overrides" keeps setter visibility at the default marker.
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, resolvedValue.getSetterVisibility());
    }
}
