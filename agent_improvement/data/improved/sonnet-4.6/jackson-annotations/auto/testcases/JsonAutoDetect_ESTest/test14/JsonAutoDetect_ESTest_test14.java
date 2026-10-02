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
public class JsonAutoDetect_ESTest_test14 extends JsonAutoDetect_ESTest_scaffolding {

    /**
     * Verifies that applying NO_OVERRIDES as an override to itself returns the same
     * instance unchanged, preserving DEFAULT getter visibility.
     *
     * withOverrides() short-circuits when the override argument is the same object
     * as the receiver, so NO_OVERRIDES.withOverrides(NO_OVERRIDES) returns NO_OVERRIDES,
     * whose getter visibility is DEFAULT.
     */
    @Test(timeout = 4000)
    public void test14() throws Throwable {
        JsonAutoDetect.Value noOverrides = JsonAutoDetect.Value.NO_OVERRIDES;

        JsonAutoDetect.Value result = noOverrides.withOverrides(noOverrides);

        assertEquals(
            "Applying NO_OVERRIDES to itself should leave getter visibility as DEFAULT",
            JsonAutoDetect.Visibility.DEFAULT,
            result.getGetterVisibility()
        );
    }
}
