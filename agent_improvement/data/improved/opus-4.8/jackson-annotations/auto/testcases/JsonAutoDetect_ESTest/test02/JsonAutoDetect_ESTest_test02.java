package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonAutoDetect_ESTest_test02 extends JsonAutoDetect_ESTest_scaffolding {

    /**
     * Merging an "empty" Value (no overrides) with a Value that overrides only the
     * is-getter visibility should yield a brand new Value that carries that single
     * override while leaving every other accessor at {@code DEFAULT}.
     */
    @Test(timeout = 4000)
    public void mergeAppliesIsGetterOverrideAndLeavesOtherAccessorsDefault() throws Throwable {
        // Base: empty value where all accessors are Visibility.DEFAULT.
        JsonAutoDetect.Value base = JsonAutoDetect.Value.noOverrides();

        // Override: only the is-getter visibility is set to PROTECTED_AND_PUBLIC.
        JsonAutoDetect.Value isGetterOverride = JsonAutoDetect.Value.construct(
                PropertyAccessor.IS_GETTER, JsonAutoDetect.Visibility.PROTECTED_AND_PUBLIC);

        JsonAutoDetect.Value merged = JsonAutoDetect.Value.merge(base, isGetterOverride);

        // Merge produces a distinct instance, not the override itself.
        assertNotSame(merged, isGetterOverride);

        // The single override is applied...
        assertEquals(JsonAutoDetect.Visibility.PROTECTED_AND_PUBLIC, merged.getIsGetterVisibility());

        // ...while all other accessors remain at DEFAULT.
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, merged.getGetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, merged.getCreatorVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, merged.getScalarConstructorVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, merged.getSetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, merged.getFieldVisibility());
    }
}
