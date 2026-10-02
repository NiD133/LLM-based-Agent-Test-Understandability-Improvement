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
public class JsonAutoDetect_ESTest_test15 extends JsonAutoDetect_ESTest_scaffolding {

    /**
     * Verifies that calling withOverrides(null) on a no-overrides Value returns an instance
     * whose field visibility remains DEFAULT (i.e., null overrides are treated as a no-op).
     */
    @Test(timeout = 4000)
    public void test15() throws Throwable {
        // A Value with all visibilities set to DEFAULT (no overrides applied)
        JsonAutoDetect.Value noOverridesValue = JsonAutoDetect.Value.noOverrides();

        // Passing null as overrides should be a no-op, returning the same Value
        JsonAutoDetect.Value valueAfterNullOverride = noOverridesValue.withOverrides((JsonAutoDetect.Value) null);

        // Field visibility must still be DEFAULT since no real overrides were applied
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, valueAfterNullOverride.getFieldVisibility());
    }
}
