package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;

import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Value;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonAutoDetect_ESTest_test03 extends JsonAutoDetect_ESTest_scaffolding {

    /**
     * Merging a "no overrides" base with a Value that only overrides the
     * getter visibility should yield a Value where only the getter visibility
     * is taken from the override; every other accessor stays at DEFAULT.
     */
    @Test(timeout = 4000)
    public void mergeAppliesOnlyTheGetterOverride() throws Throwable {
        // Base value leaves every accessor at Visibility.DEFAULT.
        Value base = Value.noOverrides();

        // Override that sets ONLY the getter visibility to NON_PRIVATE.
        Value getterOverride = Value.construct(PropertyAccessor.GETTER, Visibility.NON_PRIVATE);

        Value merged = Value.merge(base, getterOverride);

        // Only the getter visibility is overridden; the rest remain DEFAULT.
        assertEquals(Visibility.NON_PRIVATE, merged.getGetterVisibility());
        assertEquals(Visibility.DEFAULT, merged.getCreatorVisibility());
        assertEquals(Visibility.DEFAULT, merged.getIsGetterVisibility());
        assertEquals(Visibility.DEFAULT, merged.getSetterVisibility());
        assertEquals(Visibility.DEFAULT, merged.getScalarConstructorVisibility());
    }
}
