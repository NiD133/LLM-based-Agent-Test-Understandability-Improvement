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
public class JsonAutoDetect_ESTest_test03 extends JsonAutoDetect_ESTest_scaffolding {

    /**
     * Verifies that merging a no-overrides base with a getter-only override
     * sets getter visibility to NON_PRIVATE while leaving all other accessors at DEFAULT.
     */
    @Test(timeout = 4000)
    public void test03() throws Throwable {
        // Base value with all visibilities set to DEFAULT (no overrides)
        JsonAutoDetect.Value baseNoOverrides = JsonAutoDetect.Value.noOverrides();

        // Override value that sets only the GETTER accessor to NON_PRIVATE visibility
        JsonAutoDetect.Value getterNonPrivateOverride = JsonAutoDetect.Value.construct(
                PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NON_PRIVATE);

        // Merge: base provides defaults; override applies only the getter change
        JsonAutoDetect.Value merged = JsonAutoDetect.Value.merge(baseNoOverrides, getterNonPrivateOverride);

        // The getter accessor should reflect the override
        assertEquals(JsonAutoDetect.Visibility.NON_PRIVATE, merged.getGetterVisibility());

        // All other accessors remain at DEFAULT because the override did not touch them
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, merged.getCreatorVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, merged.getIsGetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, merged.getSetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, merged.getScalarConstructorVisibility());
    }
}
