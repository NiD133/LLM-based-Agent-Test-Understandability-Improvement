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
public class JsonAutoDetect_ESTest_test02 extends JsonAutoDetect_ESTest_scaffolding {

    /**
     * Verifies that merging a "no-overrides" base Value with a Value that sets only
     * IS_GETTER visibility to PROTECTED_AND_PUBLIC yields a new Value where:
     *  - isGetterVisibility is PROTECTED_AND_PUBLIC (the override takes effect)
     *  - all other visibility settings remain DEFAULT (the base has DEFAULT for all,
     *    and the override leaves them as DEFAULT, so nothing changes them)
     */
    @Test(timeout = 4000)
    public void test02() throws Throwable {
        // Base: all visibility settings are DEFAULT (no overrides applied)
        JsonAutoDetect.Value baseNoOverrides = JsonAutoDetect.Value.noOverrides();

        // Override: only IS_GETTER is set to PROTECTED_AND_PUBLIC; everything else is DEFAULT
        JsonAutoDetect.Value isGetterOverride = JsonAutoDetect.Value.construct(
                PropertyAccessor.IS_GETTER, JsonAutoDetect.Visibility.PROTECTED_AND_PUBLIC);

        // Merge: non-DEFAULT overrides win; DEFAULT overrides leave the base value unchanged
        JsonAutoDetect.Value merged = JsonAutoDetect.Value.merge(baseNoOverrides, isGetterOverride);

        // merged must be a distinct instance from the override value
        assertNotSame(merged, isGetterOverride);

        // IS_GETTER visibility was overridden to PROTECTED_AND_PUBLIC
        assertEquals(JsonAutoDetect.Visibility.PROTECTED_AND_PUBLIC, merged.getIsGetterVisibility());

        // All other visibility settings stay at DEFAULT because neither base nor override changed them
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, merged.getGetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, merged.getCreatorVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, merged.getScalarConstructorVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, merged.getSetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, merged.getFieldVisibility());
    }
}
